package com.codessquad.qna.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    Page<Question> findAllByDeletedFalse(Pageable pageable);

    Optional<Question> findByIdAndDeletedFalse(Long id);

    List<Question> findAllByDeletedFalse();
}
