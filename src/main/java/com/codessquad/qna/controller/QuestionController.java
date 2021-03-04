package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class QuestionController {
    QuestionRepository questionRepository = new QuestionRepository();

    @GetMapping("/questions/form")
    public String createForm(){
        return "/questions/form";
    }

    @GetMapping("/")
    public String createQuestionList(Question question, Model model){
        Logger logger = LoggerFactory.getLogger(QuestionController.class);
        List<Question> questions = questionRepository.getAll();

        model.addAttribute("questions", questions);
        logger.info("Questions in questionRepository: " + questions.toString());

        return "/index";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question){
        Logger logger = LoggerFactory.getLogger(QuestionController.class);
        Question newQuestion = new Question();

        newQuestion.setWriter(question.getWriter());
        newQuestion.setTitle(question.getTitle());
        newQuestion.setContents(question.getContents());

        questionRepository.save(newQuestion);

        logger.info("Question in questionRepository: " + newQuestion);

        return "redirect:/";
    }


}
