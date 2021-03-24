package com.codessquad.qna.web.domain.repository;

import com.codessquad.qna.web.domain.Answer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

}
