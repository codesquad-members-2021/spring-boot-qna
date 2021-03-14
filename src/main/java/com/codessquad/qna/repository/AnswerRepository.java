package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
}
