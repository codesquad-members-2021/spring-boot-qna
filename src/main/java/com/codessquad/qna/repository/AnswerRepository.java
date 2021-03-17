package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Answer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    @Query("SELECT answer FROM Answer answer WHERE answer.deleted = false and answer.question.id = :questionId")
    List<Answer> findActiveAnswer(Long questionId);
}
