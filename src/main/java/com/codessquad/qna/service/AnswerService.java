package com.codessquad.qna.service;

import com.codessquad.qna.exception.EntityNotFoundException;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.model.Answer;
import com.codessquad.qna.model.Question;
import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    public void save(Long questionId, Answer answer, User sessionedUser) {
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessage.QUESTION_NOT_FOUND));
        answer.save(sessionedUser, question);
        answerRepository.save(answer);
    }

    public void update(Long answerId, Answer updatedAnswer, User sessionedUser) {
        Answer answer = getAnswer(answerId, sessionedUser);
        answer.update(updatedAnswer);
        answerRepository.save(answer);
    }

    public Answer getAnswer(Long answerId, User sessionedUser) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.QUESTION_NOT_FOUND));
        if (!answer.matchWriter(sessionedUser)) {
            throw new IllegalUserAccessException();
        }
        return answer;
    }
}
