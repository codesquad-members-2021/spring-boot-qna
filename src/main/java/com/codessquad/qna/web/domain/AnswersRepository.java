package com.codessquad.qna.web.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AnswersRepository extends CrudRepository<Answer, Long> {
    Optional<Answer> findByIdAndDeletedFalse(Long id);
}
