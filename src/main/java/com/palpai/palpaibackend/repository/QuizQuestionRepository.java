package com.palpai.palpaibackend.repository;

import com.palpai.palpaibackend.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
}

