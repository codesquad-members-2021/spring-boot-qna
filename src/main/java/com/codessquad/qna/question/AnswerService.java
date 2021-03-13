package com.codessquad.qna.question;

import com.codessquad.qna.user.UserDTO;

import java.util.List;

public interface AnswerService {
    void createAnswer(Answer answer);

    void deleteAnswers(List<Answer> answers);

    void deleteAnswer(Long id, UserDTO currentSessionUser);
}
