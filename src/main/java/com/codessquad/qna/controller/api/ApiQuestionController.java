package com.codessquad.qna.controller.api;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotLoggedInException;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

    @GetMapping("/form")
    public String moveToQuestionForm(Question newQuestion, HttpSession session) {
        checkSessionUser(session);

        return "question/form";
    }

    @GetMapping("/{questionId}")
    public Question showQuestionInDetail(@PathVariable long questionId) {
        return questionService.getOneById(questionId);
    }

    @GetMapping("/{questionId}/form")
    public String moveToUpdateForm(@PathVariable long questionId, Model model, HttpSession session) {
        checkSessionUser(session);

        Question question = questionService.getOneById(questionId);

        checkAccessibleSessionUser(session, question);

        model.addAttribute("question", question);
        return "question/update";
    }

    @PutMapping("/{questionId}")
    public String updateQuestion(@PathVariable long questionId, Question referenceQuestion, HttpSession session, Model model) {
        checkSessionUser(session);

        Question question = questionService.getOneById(questionId);

        checkAccessibleSessionUser(session, question);

        questionService.updateInfo(question, referenceQuestion);
        return "redirect:/";
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(@PathVariable long questionId, HttpSession session, Model model) {
        checkSessionUser(session);

        Question question = questionService.getOneById(questionId);

        checkAccessibleSessionUser(session, question);

        questionService.remove(question);
        return "redirect:/";
    }

    private void checkSessionUser(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new NotLoggedInException();
        }
    }

    private void checkAccessibleSessionUser(HttpSession session, Question question) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isEqualWriter(sessionUser)) {
            throw new NotLoggedInException("자신의 글만 수정 및 삭제가 가능합니다.");
        }
    }
}
