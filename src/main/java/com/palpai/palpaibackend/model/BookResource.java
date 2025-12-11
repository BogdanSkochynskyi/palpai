package com.palpai.palpaibackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResource {
    private String title;
    private String author;
    private Integer year;
    private String url;
    private String coverUrl;
    private String workKey;
}
