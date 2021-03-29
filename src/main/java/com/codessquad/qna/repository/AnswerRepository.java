package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByDeletedIsFalse();
}
