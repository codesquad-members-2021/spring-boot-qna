package com.codessquad.qna.repository;

import com.codessquad.qna.model.Answer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    Optional<Answer> findByIdAndDeletedFalse(Long id);

}
