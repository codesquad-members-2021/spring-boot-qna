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
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {

        if(!HttpSessionUtils.isLoginUser(session)) {//ok

            return "/users/loginForm";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);

        Question question = questionService.findQuestion(id).get();

        if(!question.isSameWriter(loginUser)) {//ok

            return "/users/loginForm";//->본인글만 삭제 가
        }

        model.addAttribute("question", questionService.findQuestion(id).get());

        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String content, HttpSession session) {

        if(!HttpSessionUtils.isLoginUser(session)) {

            return "/qna/form";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);

        Question question = questionService.findQuestion(id).get();

        if(!question.isSameWriter(loginUser)) {

            return "/users/loginForm";
        }

        question.updqte(title, content);

        questionService.registerQuestion(question);

        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {

        if(!HttpSessionUtils.isLoginUser(session)) {//!login -> out

            return "/qna/form";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);

        Question question = questionService.findQuestion(id).get();

        if(!question.isSameWriter(loginUser)) {//not same user -> out

            return "/users/loginForm";
        }

        questionService.delete(id);//login & same user -> delete

        return "redirect:/";
    }

}
