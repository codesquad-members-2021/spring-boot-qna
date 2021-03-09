package com.codessquad.qna.web.answers;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswersRepository  extends CrudRepository<Answer, Long> {
    List findByQuestionId(long questionId);
}
