package com.palpai.palpaibackend.service;

import com.palpai.palpaibackend.response.BookDetailsResponse;
import com.palpai.palpaibackend.response.LearningResourcesResponse;
import com.palpai.palpaibackend.model.BookResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LearningResourcesService {

    public static final String URL = "https://en.wikipedia.org/api/rest_v1/page/summary/";
    private final RestTemplate restTemplate;

    public LearningResourcesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public LearningResourcesResponse getResources(String topic) {
        Map<String, String> wikiData = fetchWikiData(topic);
        String wikiTitle = wikiData.get("title");
        String wikiDescription = wikiData.get("description");
        String wikiSummary = wikiData.get("extract");
        String wikiImageUrl = wikiData.get("thumbnail");

        List<BookResource> books = fetchBooks(topic);

        return new LearningResourcesResponse(
                topic,
                wikiTitle,
                wikiDescription,
                wikiSummary,
                wikiImageUrl,
                books
        );

    }

    @SuppressWarnings("unchecked")
    private Map<String, String> fetchWikiData(String topic) {
        Map<String, String> result = new HashMap<>();

        try {
            String encoded = URLEncoder.encode(topic, StandardCharsets.UTF_8);
            encoded = encoded.replace("+", "_");
            String url = URL + encoded;

            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                result.put("extract", "No information found for this topic on Wikipedia.");
                return result;
            }

            Map<String, Object> body = response.getBody();

            // title
            Object titleObj = body.get("title");
            if (titleObj != null) {
                result.put("title", titleObj.toString());
            }

            // description
            Object descObj = body.get("description");
            if (descObj != null) {
                result.put("description", descObj.toString());
            }

            // extract (main summary text)
            Object extractObj = body.get("extract");
            if (extractObj != null) {
                result.put("extract", extractObj.toString());
            } else {
                result.put("extract", "No summary text is available for this topic.");
            }

            // thumbnail.source
            Object thumbObj = body.get("thumbnail");
            if (thumbObj instanceof Map<?, ?> thumbMap) {
                Object src = thumbMap.get("source");
                if (src != null) {
                    result.put("thumbnail", src.toString());
                }
            }

        } catch (Exception e) {
            result.put("extract", "Error while loading data from Wikipedia.");
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private List<BookResource> fetchBooks(String topic) {
        try {
            String encoded = URLEncoder.encode(topic, StandardCharsets.UTF_8);
            String url = "https://openlibrary.org/search.json?q=" + encoded;

            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                return List.of();
            }

            Object docsObj = response.getBody().get("docs");
            if (!(docsObj instanceof List<?> docs)) {
                return List.of();
            }

            // Take first 5 relevant books
            return docs.stream()
                    .filter(Map.class::isInstance)
                    .map(o -> (Map<String, Object>) o)
                    .limit(5)
                    .map(this::mapToBookResource)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }

    private BookResource mapToBookResource(Map<String, Object> doc) {
        String title = Optional.ofNullable(doc.get("title"))
                .map(Object::toString)
                .orElse("Unknown title");

        String author = "Unknown author";
        Object authorObj = doc.get("author_name");
        if (authorObj instanceof List<?> authors && !authors.isEmpty()) {
            author = String.valueOf(authors.get(0));
        }

        Integer year = null;
        Object yearObj = doc.get("first_publish_year");
        if (yearObj instanceof Number number) {
            year = number.intValue();
        }

        String url = null;
        String workKey = null;
        Object keyObj = doc.get("key"); // "/works/OL45804W"
        if (keyObj != null) {
            String keyStr = keyObj.toString();
            url = "https://openlibrary.org" + keyStr;
            if (keyStr.startsWith("/works/")) {
                workKey = keyStr.substring("/works/".length()); // -> "OL45804W"
            }
        }

        String coverUrl = null;
        Object coverObj = doc.get("cover_i");
        if (coverObj instanceof Number coverId) {
            coverUrl = "https://covers.openlibrary.org/b/id/" + coverId + "-M.jpg";
        }

        return new BookResource(title, author, year, url, coverUrl, workKey);
    }

    public BookDetailsResponse getBookDetails(String workKey) {
        try {
            String url = "https://openlibrary.org/works/" + workKey + ".json";

            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new IllegalStateException("No data for work: " + workKey);
            }

            Map<String, Object> body = response.getBody();

            String title = Optional.ofNullable(body.get("title"))
                    .map(Object::toString)
                    .orElse("Unknown title");

            // description може бути String або об’єкт { value: "..." }
            String description = null;
            Object descObj = body.get("description");
            if (descObj instanceof String s) {
                description = s;
            } else if (descObj instanceof Map<?, ?> map) {
                Object value = map.get("value");
                if (value != null) {
                    description = value.toString();
                }
            }

            @SuppressWarnings("unchecked")
            List<String> subjects = body.containsKey("subjects")
                    ? ((List<Object>) body.get("subjects")).stream()
                    .map(Object::toString)
                    .toList()
                    : List.of();

            @SuppressWarnings("unchecked")
            List<String> subjectPlaces = body.containsKey("subject_places")
                    ? ((List<Object>) body.get("subject_places")).stream()
                    .map(Object::toString)
                    .toList()
                    : List.of();

            @SuppressWarnings("unchecked")
            List<String> subjectPeople = body.containsKey("subject_people")
                    ? ((List<Object>) body.get("subject_people")).stream()
                    .map(Object::toString)
                    .toList()
                    : List.of();

            @SuppressWarnings("unchecked")
            List<String> subjectTimes = body.containsKey("subject_times")
                    ? ((List<Object>) body.get("subject_times")).stream()
                    .map(Object::toString)
                    .toList()
                    : List.of();

            // covers: [6498519, ...]
            String largeCoverUrl = null;
            Object coversObj = body.get("covers");
            if (coversObj instanceof List<?> covers && !covers.isEmpty()) {
                Object first = covers.get(0);
                if (first instanceof Number coverId) {
                    largeCoverUrl = "https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg";
                }
            }

            return new BookDetailsResponse(
                    workKey,
                    title,
                    description,
                    subjects,
                    subjectPlaces,
                    subjectPeople,
                    subjectTimes,
                    largeCoverUrl
            );
        } catch (Exception e) {
            // можна залогувати
            return new BookDetailsResponse(
                    workKey,
                    null,
                    "No detailed information available for this book.",
                    List.of(),
                    List.of(),
                    List.of(),
                    List.of(),
                    null
            );
        }
    }



}

