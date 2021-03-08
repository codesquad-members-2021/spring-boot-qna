package com.codessquad.qna.domain;

import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    Iterable<Answer> findAllByQuestion(Question question);
    int countByQuestion(Question question);
}
