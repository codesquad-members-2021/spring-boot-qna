package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/questions")
public class QuestionController {

    private QuestionService questionService;
    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("")
    public String createQuestion(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        questionService.write(question, HttpSessionUtils.getUserFromSession(session));
        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public String renderQuestion(@PathVariable Long questionId, Model model) {
        Question findQuestion = questionService.findById(questionId);

        model.addAttribute("question", findQuestion);
        return "qna/show";
    }

    @GetMapping("/form")
    public String renderQuestionForm(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        return "qna/form";
    }

    @GetMapping("/{id}/form")
    public String renderUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/";
        }
        //todo : 작성 유저가 맞는지

        Question findQuestion = questionService.findById(id);

        model.addAttribute("question", findQuestion);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String questionUpdate(@PathVariable Long id, Question question) {
        questionService.update(id, question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String questionDelete(@PathVariable Long id) {
        questionService.delete(id);
        //todo : 삭제하려는 사람이 작성자이어야 한다.
        return "redirect:/";
    }

}
