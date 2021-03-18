package com.codessquad.qna.controller;

import com.codessquad.qna.entity.Question;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.UserNotFoundInSessionException;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.util.HttpSessionUtil;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questionService.getQuestions());
        return "qna/list";
    }

    @PostMapping
    public String create(String title, String contents, HttpSession session) {
        User user = HttpSessionUtil.getUser(session).orElseThrow(UserNotFoundInSessionException::new);
        Question toCreate = new Question(user, title, contents);
        questionService.addQuestion(toCreate);
        return "redirect:/";
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtil.hasUser(session)) {
            throw new UserNotFoundInSessionException();
        }
        return "qna/form";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "qna/show";
    }
}
