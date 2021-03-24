package com.codessquad.qna.question;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findAll();
    List<Question> findAllByDeletedFalse();
}
