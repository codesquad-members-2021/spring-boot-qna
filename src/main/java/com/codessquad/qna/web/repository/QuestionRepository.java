package com.codessquad.qna.web.repository;

import com.codessquad.qna.web.domain.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    @Override
    List<Question> findAll();
}
