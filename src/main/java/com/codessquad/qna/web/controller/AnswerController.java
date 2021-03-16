package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.answer.AnswerRepository;
import com.codessquad.qna.web.domain.question.QuestionRepository;
import org.springframework.stereotype.Controller;

@Controller
public class AnswerController {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;


    public AnswerController(AnswerRepository answerRepository, QuestionRepository questionRepository){
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }


}
