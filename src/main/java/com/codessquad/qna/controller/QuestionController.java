package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public String createQuestion(Question newQuestion) {

        if(!isValidQuestion(newQuestion)){
            return "/question/form";
        }

        Question question = questionRepository.save(newQuestion);

        logger.info("after save" + question.toString());

        return "redirect:/";
    }

    private boolean isValidQuestion(Question question) {
        if (question == null){
            return false;
        }
        if ("".equals(question.getWriter()) || question.getWriter() == null) {
            return false;
        }
        if ("".equals(question.getTitle()) || question.getTitle() == null) {
            return false;
        }
        if ("".equals(question.getContents()) || question.getContents() == null) {
            return false;
        }

        return true;
    }

    @GetMapping("/{id}")
    public ModelAndView showQuestionInDetail(@PathVariable long id){
        ModelAndView modelAndView = new ModelAndView("/question/show");
        modelAndView.addObject("question", questionRepository.findById(id).get());

        return modelAndView;
    }
}
