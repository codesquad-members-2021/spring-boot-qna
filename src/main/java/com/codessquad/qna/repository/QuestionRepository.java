package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface QuestionRepository extends CrudRepository<Question, Long> {

    @Override
    List<Question> findAll();
}
