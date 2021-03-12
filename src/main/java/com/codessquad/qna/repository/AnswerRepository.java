package com.codessquad.qna.repository;

import com.codessquad.qna.model.Answer;
import com.codessquad.qna.model.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    List<Answer> findAllByQuestion(Question question);

}
