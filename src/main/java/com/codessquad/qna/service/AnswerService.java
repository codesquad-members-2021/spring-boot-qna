package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NoQuestionException;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.web.HttpSessionUtils;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private AnswerRepository answerRepository;
    private QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, QuestionService questionService) {
        this.questionService = questionService;
        this.answerRepository = answerRepository;
    }

    public Answer save(User user, Long questionId, String contents) {
        Question question = questionService.getQuestionById(questionId);
        Answer answer = new Answer(user, question, contents);

        question.addAnswer(answer);
        return answerRepository.save(answer);
    }

    public Answer getAnswerById(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(NoQuestionException::new);
    }


    public void delete(Answer answer, Long questionId) {
        Question question = questionService.getQuestionById(questionId);
        question.deleteAnswer(answer);
        answerRepository.delete(answer);
    }
}
