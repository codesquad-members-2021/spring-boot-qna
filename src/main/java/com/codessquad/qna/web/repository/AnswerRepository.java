package com.codessquad.qna.web.repository;

import com.codessquad.qna.web.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
