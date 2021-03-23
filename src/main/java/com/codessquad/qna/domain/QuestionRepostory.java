package com.codessquad.qna.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepostory extends JpaRepository<Question, Long> {
}
