package com.codesquad.qna.service;

import com.codesquad.qna.domain.Answer;
import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.exception.IllegalUserAccessException;
import com.codesquad.qna.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    public void save(Long questionId, String contents, User sessionedUser) {
        Question question = findQuestionById(questionId);
        Answer answer = new Answer(sessionedUser, question, contents);
        save(answer);
    }

    private void save(Answer answer) {
        answerRepository.save(answer);
    }

    public Answer findAnswerById(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void delete(User user, Answer answer) {
        if (!user.isMatchedAnswer(answer)) {
            throw new IllegalUserAccessException();
        }
        answer.delete();
        answerRepository.save(answer);
    }

    public Question findQuestionById(Long questionId) {
        return questionService.findQuestionById(questionId);
    }

}
