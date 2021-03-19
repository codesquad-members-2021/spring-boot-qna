package com.codessquad.qna.controller;

import com.codessquad.qna.HttpSessionUtils;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.dto.QuestionDto;
import com.codessquad.qna.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions/form")
    public String viewQuestionForm(HttpSession session){
        if(!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(QuestionDto questionDto) {
        questionService.create(questionDto);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", questionService.findQuestions());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String viewQuestion(@PathVariable long id, Model model) {
        model.addAttribute("question", questionService.findQuestionById(id));
        return "qna/show";
    }

    @PutMapping("/questions/{id}/form")
    public String viewUpdateQuestionForm(@PathVariable long id, Model model, HttpSession session){
        if (!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if(!sessionedUser.isMatchingId(id)){
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        model.addAttribute("question",questionService.findQuestionById(id));
        return "questions/";
    }
}
