package com.codessquad.qna.service;

import com.codessquad.qna.model.Answer;
import com.codessquad.qna.model.Question;
import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codessquad.qna.controller.HttpSessionUtils.getUserFromSession;

@Service
public class AnswerService {

    @Autowired
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    public void save(Long id, Answer answer, HttpSession session) {
        User loginUser = getUserFromSession(session);
        Question question = questionService.findById(id);
        if (loginUser.nonNull() && question.nonNull()) {
            answer.save(loginUser.getUserId(), question);
            this.answerRepository.save(answer);
        }
    }

    public boolean update(Long id, Answer answer, HttpSession session) {
        Answer targetAnswer = verifyAnswer(id, session);
        if (targetAnswer.nonNull()) {
            targetAnswer.update(answer);
            this.answerRepository.save(targetAnswer);
            return true;
        }
        return false;
    }

    public void delete(Long id, HttpSession session) {
        Answer targetAnswer = verifyAnswer(id, session);
        if (targetAnswer.nonNull()) {
            this.answerRepository.delete(targetAnswer);
        }
    }

    public Answer verifyAnswer(Long id, HttpSession session) {
        User loginUser = getUserFromSession(session);
        Answer targetAnswer = findById(id);
        if (loginUser.nonNull() && targetAnswer.nonNull() && targetAnswer.matchWriter(loginUser)) {
            return targetAnswer;
        }
        return new Answer();
    }

    public List<Answer> findAll(Long questionId) {
        Question question = this.questionService.findById(questionId);
        if (question.nonNull()) {
            return answerRepository.findAllByQuestion(question);
        }
        return new ArrayList<>();
    }

    public Answer findById(Long id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        return answer.orElseGet(Answer::new);
    }

    public Long findQuestionId(Long answerId) {
        Answer answer = findById(answerId);
        if (answer.nonNull()) {
            return answer.getQuestionId();
        }
        return (long) -1;
    }

}
