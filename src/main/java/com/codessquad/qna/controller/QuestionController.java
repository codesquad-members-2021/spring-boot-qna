package com.codessquad.qna.controller;

import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.utils.HttpSessionUtils;
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
    public String questionRegister(String title, String content, HttpSession session) {
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
        model.addAttribute("question", questionService.findQuestion(id));

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
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionService.findQuestion(id);

        hasPermission(session, question);

        model.addAttribute("question", questionService.findQuestion(id));

        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String content, HttpSession session) {
        Question question = questionService.findQuestion(id);

        hasPermission(session, question);

        question.update(title, content);

        questionService.registerQuestion(question);

        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        Question question = questionService.findQuestion(id);

        hasPermission(session, question);

        questionService.delete(id);

        return "redirect:/";
    }

    public void hasPermission(HttpSession session, Question question) {

        User loginUser = HttpSessionUtils.getUserFromSession(session);

        if(!question.isSameWriter(loginUser)) {
            throw new NotFoundException();
        }
    }


}
