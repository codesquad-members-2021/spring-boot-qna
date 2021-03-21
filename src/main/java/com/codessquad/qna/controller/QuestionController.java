package com.codessquad.qna.controller;

import com.codessquad.qna.HttpSessionUtils;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QuestionService;
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

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("")
    public String question(Question question){

        questionService.registerQuestion(question);

        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String showQuestionDetail(@PathVariable Long index, Model model) {

        model.addAttribute("question", questionService.findQuestion(index).get());

        return "/qna/show";
    }

    @GetMapping("/qna/form")
    public String qnsForm(HttpSession session) {
        if(HttpSessionUtils.isLoginUser(session)) {

            return "/qna/form";
        }

        return "/users/loginForm";
    }

}
