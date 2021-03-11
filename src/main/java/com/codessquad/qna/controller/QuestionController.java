package com.codessquad.qna.controller;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
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
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        return "/qna/form";
    }

    @PostMapping("/")
    public String createQuestion(Question question, HttpSession session) {
        questionService.create(question, session);
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
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }

        Question question = questionService.findById(id);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isWrittenBy(sessionedUser)) {
            throw new IllegalStateException("자신이 작성한 글만 수정할 수 있습니다.");
        }

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
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        questionService.delete(id, session);
        return "redirect:/";
    }
}
