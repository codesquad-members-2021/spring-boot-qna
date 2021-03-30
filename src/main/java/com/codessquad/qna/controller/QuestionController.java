package com.codessquad.qna.controller;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.exception.NotAuthorizationException;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.utils.HttpSessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/questions")
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @GetMapping("/form")
    public String getQuestionForm(HttpSession session) {
        HttpSessionUtils.isLoginUser(session);
        return "/qna/form";
    }

    @PostMapping("/")
    public String createQuestion(Question question, HttpSession session) {
        User sessionedUser = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        question.setWriter(sessionedUser);
        questionService.create(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getQuestion(@PathVariable Long id, Model model) {
        Question question = questionService.findById(id);
        List<Answer> answers = answerService.findAllByQuestionId(id);
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable Long id, HttpSession session, Model model) {
        Question question = questionService.findById(id);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        question.isWrittenBy(sessionedUser);
        model.addAttribute("question", question);
        return "/qna/update_form";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question questionWithUpdatedInfo) {
        questionService.update(id, questionWithUpdatedInfo);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        Question question = questionService.findById(id);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        question.isWrittenBy(sessionedUser);
        questionService.deleteById(id);
        return "redirect:/";
    }
}
