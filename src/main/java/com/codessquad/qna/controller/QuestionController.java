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
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @GetMapping("/form")
    public String viewQuestionForm(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "redirect:/users/loginForm";
        return "qna/form";
    }

    @PostMapping
    public String createQuestion(QuestionDto questionDto, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        questionService.create(questionDto, sessionedUser);
        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questionService.findQuestions());
        return "index";
    }

    @GetMapping("/{questionId}")
    public String viewQuestion(@PathVariable long questionId, Model model) {
        model.addAttribute("question", questionService.findQuestionById(questionId));
        model.addAttribute("answers", answerService.findAnswersByQuestionId(questionId));
        return "qna/show";
    }

    @GetMapping("/{questionId}/form")
    public String viewUpdateQuestionForm(@PathVariable long questionId, Model model, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionService.findQuestionById(questionId);
        model.addAttribute("question", question);
        if (!question.isMatchingWriter(sessionedUser)) {
            model.addAttribute("error", "자신의 질문만 수정할 수 있습니다.");
            model.addAttribute("answers", answerService.findAnswersByQuestionId(questionId));
            return "qna/show";
        }

        return "qna/updateForm";
    }

    @PutMapping("/{questionId}")
    public String updateQuestion(@PathVariable long questionId, QuestionDto updateQuestionDto, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question updateQuestion = updateQuestionDto.toEntity(sessionedUser);
        questionService.update(updateQuestion, questionId, sessionedUser);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{questionId}")
    public String delete(@PathVariable long questionId, HttpSession session, Model model) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionService.findQuestionById(questionId);
        if (!questionService.delete(question, sessionedUser)) {
            model.addAttribute("error", "자신의 질문만 삭제할 수 있습니다.");
            model.addAttribute("question", question);
            model.addAttribute("answers", answerService.findAnswersByQuestionId(questionId));
            return "qna/show";
        }
        return "redirect:/questions";
    }
}
