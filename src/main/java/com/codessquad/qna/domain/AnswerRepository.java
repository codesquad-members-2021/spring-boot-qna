package com.codessquad.qna.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Transactional(readOnly = true)
    List<Answer> findAnswersByQuestionId(long id);
}
