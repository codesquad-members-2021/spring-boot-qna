package com.codessquad.qna.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    Optional<Question> findByIdAndDeleted(Long id, boolean deleted);
    List<Question> findAllByDeleted(boolean deleted);
}
