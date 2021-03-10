package com.codessquad.qna.web.repository;

import com.codessquad.qna.web.domain.Question;

import java.util.List;

public interface QuestionRepository  {
    void save(Question question);

    List<Question> findAll();

    Question findByIndex(int index);
}
