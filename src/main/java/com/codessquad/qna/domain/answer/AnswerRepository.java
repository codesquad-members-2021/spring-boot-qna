package com.codessquad.qna.domain.answer;

import com.codessquad.qna.domain.question.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findByQuestion(Question question);
}
