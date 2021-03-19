package com.codessquad.qna.web.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findAllByDeletedFalse();
    Optional<Question> findByIdAndDeletedFalse(Long id);
}
