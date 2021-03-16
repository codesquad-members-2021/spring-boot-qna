package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public String createQuestion(Question newQuestion) {

        if (!isValidQuestion(newQuestion)) {
            return "question/form";
        }

        questionService.add(newQuestion);

        return "redirect:/";
    }

    @GetMapping("/form")
    public String moveToQuestionForm(Question newQuestion, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "user/login";
        }
        return "question/form";
    }

    private boolean isValidQuestion(Question question) {
        if (question == null) {
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
    public String showQuestionInDetail(@PathVariable long id, Model model) {
        model.addAttribute("question", questionService.getOneById(id).orElse(null));

        return "question/show";
    }
}
