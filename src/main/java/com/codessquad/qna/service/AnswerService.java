package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.User;

public interface AnswerService {

    void createAnswer(Long questionId, Answer answer, User writer);

    void deleteAnswer(Long questionId, Long answerId, User loginUser);

    Answer getAnswerWithAuthentication(Long questionId, Long answerId, User loginUser);

    void updateAnswer(Long questionId, Long answerId, User loginUser, Answer updatedAnswer);
}
