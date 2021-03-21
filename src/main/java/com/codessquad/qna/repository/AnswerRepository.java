package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Answer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    List<Answer> findAllByAndDeletedFalse();

    Optional<Answer> findByAnswerIdAndDeletedFalse(Long id);

    @Override
    List<Answer> findAll();
}
