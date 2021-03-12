package com.codessquad.qna.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findAllByQuestion(Question question);

    int countByQuestion(Question question);

    Optional<Answer> findByIdAndQuestionId(Long answerId, Long questionId);
}
