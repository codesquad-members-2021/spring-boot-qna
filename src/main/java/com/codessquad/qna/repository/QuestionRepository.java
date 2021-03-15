package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    @Query("SELECT question FROM Question question WHERE question.deleted = false")
    List<Question> findAllActive();
}
