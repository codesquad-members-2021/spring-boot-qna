package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.*;
import com.codessquad.qna.web.exceptions.InvalidEntityException;
import com.codessquad.qna.web.exceptions.answers.AnswerNotFoundException;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.exceptions.questions.QuestionNotFoundException;
import org.springframework.stereotype.Service;

import static com.codessquad.qna.web.utils.ExceptionConstants.CANNOT_MODIFY_ANOTHER_USERS_ANSWER;
import static com.codessquad.qna.web.utils.ExceptionConstants.EMPTY_FIELD_IN_ANSWER_ENTITY;

@Service
public class AnswerService {
    private final QuestionRepository questionRepository;
    private final AnswersRepository answersRepository;

    public AnswerService(QuestionRepository questionRepository, AnswersRepository answersRepository) {
        this.questionRepository = questionRepository;
        this.answersRepository = answersRepository;
    }

    public void createAnswer(User loginUser, long questionId, String answerContents) {
        Question targetQuestion = questionRepository.findByIdAndDeletedFalse(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        Answer answer = new Answer(answerContents, targetQuestion, loginUser);
        verifyAnswerEntityIsValid(answer);
        answersRepository.save(answer);
    }

    public Question deleteAnswer(User loginUser, long answerId) {
        Answer targetAnswer = answersRepository.findByIdAndDeletedFalse(answerId)
                .orElseThrow(AnswerNotFoundException::new);
        if (!targetAnswer.isMatchingWriter(loginUser)) {
            throw new UnauthorizedAccessException(CANNOT_MODIFY_ANOTHER_USERS_ANSWER);
        }
        this.answersRepository.delete(targetAnswer);
        return targetAnswer.getQuestion();
    }

    private void verifyAnswerEntityIsValid(Answer answer) {
        if (!answer.isValid()) {
            throw new InvalidEntityException(EMPTY_FIELD_IN_ANSWER_ENTITY);
        }
    }
}
