package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import java.util.List;

public interface QnaRepository {

    void save(Question question);

    List<Question> findAll();

    Question findQuestionById(int index);
}
