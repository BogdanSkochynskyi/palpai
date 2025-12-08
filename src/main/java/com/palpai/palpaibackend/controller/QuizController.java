package com.palpai.palpaibackend.controller;

import com.palpai.palpaibackend.request.QuizAnswerRequest;
import com.palpai.palpaibackend.response.QuizAnswerResponse;
import com.palpai.palpaibackend.response.QuizQuestionResponse;
import com.palpai.palpaibackend.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/first")
    public ResponseEntity<QuizQuestionResponse> getFirstQuestion() {
        QuizQuestionResponse response = quizService.getFirstQuestion();
        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/next/{currentId}")
    public ResponseEntity<QuizQuestionResponse> getNextQuestion(@PathVariable Long currentId) {
        QuizQuestionResponse response = quizService.getNextQuestion(currentId);
        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/answer/{questionId}")
    public QuizAnswerResponse checkAnswer(@PathVariable Long questionId,
                                          @RequestBody QuizAnswerRequest request) {
        return quizService.checkAnswer(questionId, request.getSelectedOption());
    }
}

