package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    @Override
    List<Question> findAll();
}
