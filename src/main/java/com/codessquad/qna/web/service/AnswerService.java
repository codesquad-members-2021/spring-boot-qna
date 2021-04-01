package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.*;
import com.codessquad.qna.web.exceptions.answers.AnswerNotFoundException;
import com.codessquad.qna.web.exceptions.auth.ForbiddenAccessException;
import com.codessquad.qna.web.exceptions.questions.QuestionNotFoundException;
import org.springframework.stereotype.Service;

import static com.codessquad.qna.web.exceptions.auth.ForbiddenAccessException.CANNOT_MODIFY_ANOTHER_USERS_ANSWER;

@Service
public class AnswerService {
    private final QuestionRepository questionRepository;
    private final AnswersRepository answersRepository;

    public AnswerService(QuestionRepository questionRepository, AnswersRepository answersRepository) {
        this.questionRepository = questionRepository;
        this.answersRepository = answersRepository;
    }

    public Answer createAnswer(User loginUser, long questionId, String answerContents) {
        Question targetQuestion = questionRepository.findByIdAndDeletedFalse(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        Answer answer = new Answer(answerContents, targetQuestion, loginUser);
        answer.verifyAnswerEntityIsValid();
        return answersRepository.save(answer);
    }

    public void deleteAnswer(User loginUser, long answerId) {
        Answer answer = answersRepository.findByIdAndDeletedFalse(answerId)
                .orElseThrow(AnswerNotFoundException::new);
        if (!answer.isMatchingWriter(loginUser)) {
            throw new ForbiddenAccessException(CANNOT_MODIFY_ANOTHER_USERS_ANSWER);
        }
        answer.delete();
        answersRepository.save(answer);
    }
}
