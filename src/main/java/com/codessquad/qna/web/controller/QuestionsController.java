package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.service.QuestionService;
import com.codessquad.qna.web.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionsController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionsController.class);
    private final QuestionService questionService;

    public QuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public String createQuestion(Question newQuestion, HttpSession session) {
        User loginUser = SessionUtil.getLoginUser(session);
        questionService.createQuestion(newQuestion, loginUser);
        logger.info("question created {}", newQuestion);
        return "redirect:/";
    }

    @GetMapping
    public String questionList(Model model) {
        model.addAttribute("questions", questionService.questions());
        return "index";
    }

    @GetMapping("/{questionId}")
    public String questionDetail(@PathVariable("questionId") long questionId, Model model) {
        Question foundQuestion = questionService.questionDetail(questionId);
        model.addAttribute("question", foundQuestion);
        return "qna/show";
    }

    @GetMapping("/{questionId}/modify-form")
    public String modifyForm(@PathVariable("questionId") long questionId, Model model, HttpSession session) {
        User loginUser = SessionUtil.getLoginUser(session);
        Question question = questionService.questionDetail(questionId);
        question.verifyIsQuestionOwner(loginUser);
        model.addAttribute("currentQuestion", question);
        return "qna/modify-form";
    }

    @PutMapping("/{questionId}")
    public String modifyQuestion(@PathVariable("questionId") long questionId,
                                 Question newQuestion, HttpSession session) {
        User loginUser = SessionUtil.getLoginUser(session);
        Question modifiedQuestion = questionService.modifyQuestion(loginUser, questionId, newQuestion);
        logger.info("question modified {}", modifiedQuestion);
        return "redirect:/questions/" + modifiedQuestion.getId();
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(@PathVariable("questionId") long questionId, HttpSession session) {
        User loginUser = SessionUtil.getLoginUser(session);
        questionService.deleteQuestion(loginUser, questionId);
        return "redirect:/";
    }
}

