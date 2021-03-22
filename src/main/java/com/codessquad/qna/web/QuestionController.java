package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.AccessDeniedException;
import com.codessquad.qna.exception.NoQuestionException;
import com.codessquad.qna.exception.NoUserException;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        return "qna/form";
    }

    @PostMapping
    public String createNewQuestion(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        questionService.save(sessionUser, question);

        return "redirect:/";
    }

    @GetMapping
    public String showQuestionList(Model model) {
        model.addAttribute("question", questionService.getQuestionList());
        return "index";
    }

    @GetMapping("/{id}")
    public String showOneQuestion(@PathVariable long id, Model model) {
        model.addAttribute("question", questionService.getQuestionById(id));
        return "qna/show";
    }

    @GetMapping("{id}/form")
    public String editQuestion(@PathVariable long id, Model model, HttpSession session) {
        Question question = questionService.getQuestionById(id);

        User sessionUser = HttpSessionUtils.getUserFromSession(session);

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        if (question.userConfirmation(sessionUser)) {
            throw new AccessDeniedException();
        }

        model.addAttribute("question", question);

        return "qna/updateForm";
    }

    @PostMapping("{id}")
    public String update(@PathVariable long id, Question updateQuestion) {
        questionService.updateQuestion(id, updateQuestion);
        return "redirect:/";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable long id, HttpSession session) {
        Question question = questionService.getQuestionById(id);

        User sessionUser = HttpSessionUtils.getUserFromSession(session);

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        if (question.userConfirmation(sessionUser)) {
            throw new AccessDeniedException();
        }

        questionService.delete(question);
        return "redirect:/";
    }
}
