package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestionList();

    void registerQuestion(Question question, User loginUser);

    Question getQuestionById(Long id);

    Question getQuestionWithAuthentication(Long id, User loginUser);

    void updateQuestion(Long id, User loginUser, Question updatedQuestion);

    void deleteQuestion(Long id, User loginUser);
}
