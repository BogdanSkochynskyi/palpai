package com.palpai.palpaibackend.response;

import com.palpai.palpaibackend.model.BookResource;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LearningResourcesResponse {

    private String topic;

    // Wikipedia
    private String wikiTitle;
    private String wikiDescription; // description
    private String wikiSummary;     // extract
    private String wikiImageUrl;    // thumbnail.source

    private List<BookResource> books;
}
