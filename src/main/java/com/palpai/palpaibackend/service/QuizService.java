package com.palpai.palpaibackend.service;

import com.palpai.palpaibackend.model.QuizQuestion;
import com.palpai.palpaibackend.repository.QuizQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizQuestionRepository quizQuestionRepository;

    public List<QuizQuestion> getAllQuestions() {
        return quizQuestionRepository.findAll();
    }
}

