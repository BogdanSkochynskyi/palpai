package com.palpai.palpaibackend.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookDetailsResponse {

    private String workKey;
    private String title;
    private String description;
    private List<String> subjects;
    private List<String> subjectPlaces;
    private List<String> subjectPeople;
    private List<String> subjectTimes;
    private String largeCoverUrl;
}

