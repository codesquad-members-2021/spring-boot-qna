package com.codessquad.qna.question;

import com.codessquad.qna.user.UserDTO;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestions();

    Question getQuestion(Long id);

    Question getQuestion(Long id, UserDTO currentSessionUser);

    void createQuestion(Question question, UserDTO currentSessionUser);

    void updateQuestion(Long id, Question newQuestion, UserDTO currentSessionUser);

    void deleteQuestion(Long id, UserDTO currentSessionUser);
}
