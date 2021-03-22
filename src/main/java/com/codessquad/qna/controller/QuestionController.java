package com.codessquad.qna.controller;

import com.codessquad.qna.HttpSessionUtils;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("")
    public String question(String title, String content,  HttpSession session) {

        if(!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);

        Question question = new Question(title, content, sessionedUser);

        questionService.registerQuestion(question);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showQuestionDetail(@PathVariable Long id, Model model) {

        model.addAttribute("question", questionService.findQuestion(id).get());

        return "/qna/show";
    }

    @GetMapping("/qna/form")
    public String qnaForm(HttpSession session) {
        if(HttpSessionUtils.isLoginUser(session)) {

            return "/qna/form";
        }

        return "/users/loginForm";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {

        model.addAttribute("question", questionService.findQuestion(id).get());

        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String content) {

        Question question = questionService.findQuestion(id).get();

        question.updqte(title, content);

        questionService.registerQuestion(question);

        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        questionService.delete(id);

        return "redirect:/";
    }

}
