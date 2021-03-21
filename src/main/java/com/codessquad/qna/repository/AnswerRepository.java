package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Answer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
<<<<<<< HEAD
    List<Answer> findAllByAndDeletedFalse();

    Optional<Answer> findByAnswerIdAndDeletedFalse(Long id);
=======

    List<Answer> findAllByAndDeletedFalse();

    Optional<Answer> findByAnswerIdAndDeletedFalse(Long id);

    @Override
    List<Answer> findAll();
>>>>>>> 9055b4202d9c420f7c0f8159deecd4735089e8e3
}
