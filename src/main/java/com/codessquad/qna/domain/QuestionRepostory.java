package com.codessquad.qna.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepostory extends JpaRepository<Question, Long> {
    List<Question> findAllByDeletedFalse();

    Optional<Question> findByIdAndDeletedFalse(Long id);
}
