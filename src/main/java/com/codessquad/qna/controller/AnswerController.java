package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Result;
import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.utils.HttpSessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/questions/{questionId}/answers")
@Controller
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping("/")
    public String createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        answer.setWriter(sessionedUser);
        answerService.create(questionId, answer);
        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable Long questionId, @PathVariable Long id, Model model, HttpSession session) {
        Question question = questionService.findById(questionId);
        Answer answer = answerService.findById(id);
        Result result = valid(session, answer);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/users/login";
        }
        model.addAttribute("question", question);
        model.addAttribute("answer", answer);
        return "/answers/update_form";
    }

    @PutMapping("/{id}")
    public String updateAnswer(@PathVariable Long questionId, @PathVariable Long id, Answer answerWithUpdateInfo) {
        answerService.update(id, answerWithUpdateInfo);

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{id}")
    public String deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, Model model, HttpSession session) {
        Answer answer = answerService.findById(id);
        Result result = valid(session, answer);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/users/login";
        }
        answerService.deleteById(id);
        return "redirect:/questions/" + questionId;
    }

    private Result valid(HttpSession session, Answer answer) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!answer.isWrittenBy(sessionedUser)) {
            return Result.fail("자신이 작성한 답변만 수정, 삭제할 수 있습니다.");
        }
        return Result.ok();
    }
}
