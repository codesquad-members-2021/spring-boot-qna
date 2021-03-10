package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

    void save(Question question);

    Optional<Question> findQuestionByIndex(int index);

    List<Question> findQuestionList();

}
