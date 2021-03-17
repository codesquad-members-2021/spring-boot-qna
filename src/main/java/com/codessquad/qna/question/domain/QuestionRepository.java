package com.codessquad.qna.question.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByDeleted(boolean deleted);
}
