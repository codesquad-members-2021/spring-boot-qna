package com.codessquad.qna.controller;

import com.codessquad.qna.entity.Question;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.NotAuthorizedException;
import com.codessquad.qna.exception.UserNotFoundInSessionException;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.util.HttpSessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        User user = HttpSessionUtil.getUser(session);
        questionService.addQuestion(user, title, contents);
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, String contents, String title, HttpSession session) {
        User tryToUpdate = HttpSessionUtil.getUser(session);
        questionService.updateQuestion(id, title, contents, tryToUpdate);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, HttpSession session) {
        User tryToDelete = HttpSessionUtil.getUser(session);
        questionService.deleteQuestion(id, tryToDelete);
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

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable int id, Model model, HttpSession session) {
        Question question = questionService.getQuestion(id);
        if (!HttpSessionUtil.isAuthorized(question.getWriter().getId(), session)) {
            throw new NotAuthorizedException();
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }
}
