package com.codessquad.qna.controller.api;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.util.HttpSessionUtils.checkSessionUser;
import static com.codessquad.qna.util.HttpSessionUtils.getSessionUser;

@RestController
@RequestMapping("api/questions")
public class ApiQuestionController {
    private final Logger logger = LoggerFactory.getLogger(ApiQuestionController.class);
    private final QuestionService questionService;

    public ApiQuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public String createQuestion(Question newQuestion, HttpSession session) {
        if (newQuestion.isEmpty()) {
            return "question/form";
        }

        questionService.addQuestion(newQuestion, HttpSessionUtils.getUserFromSession(session));

        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public Question showQuestionInDetail(@PathVariable long questionId) {
        return questionService.getOneById(questionId);
    }

    @PutMapping("/{questionId}")
    public String updateQuestion(@PathVariable long questionId, Question newQuestionInfo, HttpSession session, Model model) {
        checkSessionUser(session);

        questionService.updateInfo(questionService.getOneById(questionId), newQuestionInfo, getSessionUser(session));
        return "redirect:/";
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(@PathVariable long questionId, HttpSession session, Model model) {
        checkSessionUser(session);

        questionService.remove(getSessionUser(session), questionService.getOneById(questionId));
        return "redirect:/";
    }
}
