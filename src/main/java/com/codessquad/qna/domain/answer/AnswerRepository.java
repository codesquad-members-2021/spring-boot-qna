package com.codessquad.qna.domain.answer;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findAllByQuestionId(Long questionId);
}
