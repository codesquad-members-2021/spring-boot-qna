package com.codessquad.qna.service;

import com.codessquad.qna.exception.EntityNotFoundException;
import com.codessquad.qna.exception.ErrorMessage;
import com.codessquad.qna.exception.UserSessionException;
import com.codessquad.qna.model.Answer;
import com.codessquad.qna.model.Question;
import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public Answer save(Long id, Answer answer, User sessionUser) {
        Question question = this.questionRepository.findByIdAndDeletedFalse(id).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessage.QUESTION_NOT_FOUND));
        answer.save(sessionUser, question);
        return this.answerRepository.save(answer);
    }

    public void update(Long id, Answer answer, User sessionUser) {
        Answer targetAnswer = verifyAnswer(id, sessionUser);
        targetAnswer.update(answer);
        this.answerRepository.save(targetAnswer);
    }

    public Answer delete(Long id, User sessionUser) {
        Answer targetAnswer = verifyAnswer(id, sessionUser);
        targetAnswer.delete();
        return this.answerRepository.save(targetAnswer);
    }

    public Answer verifyAnswer(Long id, User sessionUser) {
        Answer targetAnswer = findById(id);
        if (!targetAnswer.matchWriter(sessionUser)) {
            throw new UserSessionException(ErrorMessage.ILLEGAL_USER);
        }
        return targetAnswer;
    }

    public Long findQuestionId(Long answerId) {
        return findById(answerId).getQuestionId();
    }

    public Answer findById(Long id) {
        return this.answerRepository.findByIdAndDeletedFalse(id).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessage.ANSWER_NOT_FOUND));
    }

}
