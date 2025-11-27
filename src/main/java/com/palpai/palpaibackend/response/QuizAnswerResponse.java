package com.palpai.palpaibackend.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizAnswerResponse {
    private boolean correct;
    private String correctOption;
}

