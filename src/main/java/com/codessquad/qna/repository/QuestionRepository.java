package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import java.util.List;

public interface QuestionRepository {

    Question save(Question question);

    List<Question> findAll();

    Question findQuestionById(Long id);
}
