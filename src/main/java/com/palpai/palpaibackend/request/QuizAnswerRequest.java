package com.palpai.palpaibackend.request;

import lombok.Data;

@Data
public class QuizAnswerRequest {
    private String selectedOption; // "A" / "B" / "C" / "D"
}

