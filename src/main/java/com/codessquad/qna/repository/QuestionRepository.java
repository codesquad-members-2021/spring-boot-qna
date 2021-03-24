package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Question;

import java.util.List;

public interface QuestionRepository extends GenericRepository<Question, Long> {
    List<Question> findAllByAndDeletedFalse();
}
