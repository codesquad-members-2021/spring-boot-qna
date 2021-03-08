package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;

import java.util.List;

public interface QuestionRepository {

    void save(Question question);

    Question findQuestionByIndex(int index);

    List<Question> findQuestionList();

}
