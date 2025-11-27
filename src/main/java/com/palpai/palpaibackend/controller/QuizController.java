package com.palpai.palpaibackend.controller;

import com.palpai.palpaibackend.model.QuizQuestion;
import com.palpai.palpaibackend.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public List<QuizQuestion> getQuizQuestions() {
        return quizService.getAllQuestions();
    }
}
