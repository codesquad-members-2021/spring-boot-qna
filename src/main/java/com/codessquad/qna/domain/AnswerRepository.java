package com.codessquad.qna.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    Optional<Answer> findByIdAndQuestionIdAndDeletedFalse(Long answerId, Long questionId);
}
