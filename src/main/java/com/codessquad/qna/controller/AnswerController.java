package com.codessquad.qna.controller;

import com.codessquad.qna.HttpSessionUtils;
import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    public AnswerController(AnswerService answerService, QuestionService questionService) {

        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping
    public String createAnswer(@PathVariable long questionId, String contents, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        answerService.create(sessionedUser, questionId, contents);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(@PathVariable long questionId, @PathVariable long answerId, HttpSession session, Model model) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = answerService.findAnswerByAnswerId(answerId);
        if (!answerService.delete(answer, sessionedUser)) {
            model.addAttribute("error", "자신의 답변만 삭제할 수 있습니다.");
            model.addAttribute("question", questionService.findQuestionById(questionId));
            model.addAttribute("answers", answerService.findAnswersByQuestionId(questionId));
            return "qna/show";
        }
        return "redirect:/questions/" + questionId;
    }
}
