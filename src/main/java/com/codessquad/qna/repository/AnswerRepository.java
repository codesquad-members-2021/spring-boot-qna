package com.codessquad.qna.repository;

import com.codessquad.qna.model.Answer;
import org.springframework.data.repository.CrudRepository;


public interface AnswerRepository extends CrudRepository<Answer, Long> {
}
