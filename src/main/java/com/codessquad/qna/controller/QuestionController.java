package com.codessquad.qna.controller;

import com.codessquad.qna.HttpSessionUtils;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.dto.QuestionDto;
import com.codessquad.qna.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions/form")
    public String viewQuestionForm(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
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

    @GetMapping("/questions/{id}/form")
    public String viewUpdateQuestionForm(@PathVariable long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";
        Question question = questionService.findQuestionById(id);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!questionService.verifyQuestion(question, sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/questions/{id}")
    public String updateQuestion(@PathVariable long id, QuestionDto updateQuestionDto, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question updateQuestion = new Question(updateQuestionDto);
        if (!questionService.verifyQuestion(updateQuestion, sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        Question question = questionService.findQuestionById(id);
        question.update(updateQuestion);
        questionService.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}")
    public String delete(@PathVariable long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionService.findQuestionById(id);
        if (!questionService.verifyQuestion(question, sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        questionService.delete(question);
        return "redirect:/";
    }
}
