package com.codessquad.qna.service;

import com.codessquad.qna.entity.Answer;
import com.codessquad.qna.entity.Question;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.repository.AnswerRepository;
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

    public void addAnswer(long questionId, User writer, String contents) {
        Question question = questionService.getQuestion(questionId);
        answerRepository.save(new Answer(question, writer, contents));
    }
}
