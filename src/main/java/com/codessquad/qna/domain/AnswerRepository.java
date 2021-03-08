package com.codessquad.qna.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    Iterable<Answer> findAllByQuestion(Question question);

    int countByQuestion(Question question);

    Optional<Answer> findByIdAndQuestionIdAndWriter(Long answerId, Long questionId, User user);
}
