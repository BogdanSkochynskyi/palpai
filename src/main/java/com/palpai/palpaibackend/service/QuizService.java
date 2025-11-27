package com.palpai.palpaibackend.service;

import com.palpai.palpaibackend.model.QuizQuestion;
import com.palpai.palpaibackend.repository.QuizQuestionRepository;
import com.palpai.palpaibackend.response.QuizAnswerResponse;
import com.palpai.palpaibackend.response.QuizQuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizQuestionRepository quizQuestionRepository;

    public QuizQuestionResponse getFirstQuestion() {
        return quizQuestionRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .findFirst()
                .map(this::toResponse)
                .orElse(null);
    }

    public QuizQuestionResponse getNextQuestion(Long currentId) {
        return quizQuestionRepository.findAll(
                        Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .filter(q -> q.getId() > currentId)
                .findFirst()
                .map(this::toResponse)
                .orElse(null);
    }

    public QuizAnswerResponse checkAnswer(Long questionId, String selectedOption) {
        QuizQuestion question = quizQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found: " + questionId));

        String correct = question.getCorrectOption();
        boolean isCorrect = correct != null &&
                correct.equalsIgnoreCase(selectedOption);

        return new QuizAnswerResponse(isCorrect, correct);
    }

    private QuizQuestionResponse toResponse(QuizQuestion q) {
        return new QuizQuestionResponse(
                q.getId(),
                q.getQuestion(),
                q.getOptionA(),
                q.getOptionB(),
                q.getOptionC(),
                q.getOptionD()
        );
    }
}


