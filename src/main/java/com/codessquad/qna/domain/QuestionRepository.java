package com.codessquad.qna.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    @Query(value = "SELECT q FROM Question q WHERE q.status = true")
     List<Question> findQuestions();
}
