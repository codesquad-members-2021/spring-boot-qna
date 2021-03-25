package com.codessquad.qna.controller;

import com.codessquad.qna.HttpSessionUtils;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.dto.QuestionDto;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @GetMapping("/questions/form")
    public String viewQuestionForm(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(QuestionDto questionDto, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)){
            return "redirect:/users/loginForm";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        questionService.create(questionDto, sessionedUser);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", questionService.findQuestions());
        return "index";
    }

    @GetMapping("/questions/{questionId}")
    public String viewQuestion(@PathVariable long questionId, Model model) {
        model.addAttribute("question", questionService.findQuestionById(questionId));
        model.addAttribute("answers", answerService.findAnswersByQuestionId(questionId));
        return "qna/show";
    }

    @GetMapping("/questions/{questionId}/form")
    public String viewUpdateQuestionForm(@PathVariable long questionId, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";
        Question question = questionService.findQuestionById(questionId);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!questionService.verifyQuestion(question, sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/questions/{questionId}")
    public String updateQuestion(@PathVariable long questionId, QuestionDto updateQuestionDto, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question updateQuestion = updateQuestionDto.toEntity(sessionedUser);
        if (!questionService.verifyQuestion(updateQuestion, sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        Question question = questionService.findQuestionById(questionId);
        question.update(updateQuestion);
        questionService.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}")
    public String delete(@PathVariable long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionService.findQuestionById(id);
        if (!questionService.verifyQuestion(question, sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        questionService.delete(question);
        return "redirect:/";
    }
}
