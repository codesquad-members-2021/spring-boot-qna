package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.Result;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/form")
    public String form(HttpSession session, Model model) {
        boolean isLoginUser = HttpSessionUtils.isLoginUser(session);

        Result result = questionService.valid(isLoginUser);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        return "qna/form";
    }

    @PostMapping
    public String createNewQuestion(Question question, HttpSession session) {
        questionService.save(HttpSessionUtils.getUserFromSession(session), question);
        return "redirect:/";
    }

    @GetMapping
    public String questionList(Model model) {
        model.addAttribute("question", questionService.getQuestionList());
        return "index";
    }

    @GetMapping("/{id}")
    public String question(@PathVariable long id, Model model) {
        model.addAttribute("question", questionService.getQuestionById(id));
        return "qna/show";
    }

    @GetMapping("{id}/form")
    public String editQuestion(@PathVariable long id, Model model, HttpSession session) {
        Question question = questionService.getQuestionById(id);
        boolean isLoginUser = HttpSessionUtils.isLoginUser(session);
        User sessionUser = HttpSessionUtils.getUserFromSession(session);

        Result result = questionService.valid(isLoginUser, sessionUser, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("{id}")
    public String updateQuestion(@PathVariable long id, Question updateQuestion) {
        questionService.updateQuestion(id, updateQuestion);
        return "redirect:/";
    }

    @DeleteMapping("{id}")
    public String deleteQuestion(@PathVariable long id, Model model, HttpSession session) {
        boolean isLoginUser = HttpSessionUtils.isLoginUser(session);
        User sessionUser = HttpSessionUtils.getUserFromSession(session);

        Result result = questionService.delete(id, isLoginUser, sessionUser);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        return "redirect:/";
    }

}
