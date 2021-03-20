package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.util.HttpSessionUtils;
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
    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @PostMapping
    public String createQuestion(Question newQuestion, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        newQuestion.setWriter(sessionUser);
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
        if (question.getWriter() == null) {
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
        Question testQuestion = questionService.getOneById(id).orElse(null);
        model.addAttribute("question", testQuestion);
        logger.info(" testQuestion " + testQuestion.getAnswers().toString());
        return "question/show";
    }

    @GetMapping("/{id}/form")
    public String moveToUpdateForm(@PathVariable long id, Model model, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)){
            return "/user/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionService.getOneById(id).orElse(null);
        if(!question.isEqualWriter(sessionUser)){
            return "/user/login";
        }

        model.addAttribute("question", question);
        return "question/update";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable long id, String title, String contents, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)){
            return "/user/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionService.getOneById(id).orElse(null);

        if(!question.isEqualWriter(sessionUser)){
            return "/user/login";
        }

        question.updateInfo(title, contents);
        questionService.add(question);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable long id, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)){
            return "/user/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionService.getOneById(id).orElse(null);

        if(!question.isEqualWriter(sessionUser)){
            return "/user/login";
        }

        questionService.remove(id);
        return "redirect:/";
    }
}
