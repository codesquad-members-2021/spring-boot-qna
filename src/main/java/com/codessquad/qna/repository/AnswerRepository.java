package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Answer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findAllByQuestionIdAndIsDeleteFalse(Long questionId);
}
