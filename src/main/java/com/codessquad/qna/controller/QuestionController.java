package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.Result;
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

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public String createQuestion(Question newQuestion, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        newQuestion.setWriter(sessionUser);

        if (newQuestion.isEmpty()) {
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

    @GetMapping("/{questionId}")
    public String showQuestionInDetail(@PathVariable long questionId, Model model) {
        Question testQuestion = questionService.getOneById(questionId).orElse(null);
        model.addAttribute("question", testQuestion);
        logger.info(" testQuestion " + testQuestion.getAnswers().toString());
        return "question/show";
    }

    @GetMapping("/{questionId}/form")
    public String moveToUpdateForm(@PathVariable long questionId, Model model, HttpSession session) {
        Question question = questionService.getOneById(questionId).orElse(null);
        Result result = checkSession(session, question);

        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        model.addAttribute("question", question);
        return "question/update";
    }

    @PutMapping("/{questionId}")
    public String updateQuestion(@PathVariable long questionId, Question referenceQuestion, HttpSession session, Model model) {
        Question question = questionService.getOneById(questionId).orElse(null);
        Result result = checkSession(session, question);

        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        questionService.updateInfo(question, referenceQuestion);
        return "redirect:/";
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(@PathVariable long questionId, HttpSession session, Model model) {
        Question question = questionService.getOneById(questionId).orElse(null);
        Result result = checkSession(session, question);

        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        questionService.remove(questionId);
        return "redirect:/";
    }

    private Result checkSession(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isEqualWriter(sessionUser)) {
            return Result.fail("자신이 쓴 글만 수정 및 삭제가 가능합니다.");
        }

        return Result.ok();
    }
}
