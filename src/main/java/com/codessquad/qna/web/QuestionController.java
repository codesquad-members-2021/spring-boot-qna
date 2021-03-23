package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.AccessDeniedException;
import com.codessquad.qna.exception.NoQuestionException;
import com.codessquad.qna.exception.NoUserException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/question")
public class QuestionController {
    private final QuestionRepository qnaRepository;

    public QuestionController(QuestionRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
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
        question.setWriter(sessionUser);

        qnaRepository.save(question);
        return "redirect:/";
    }

    @GetMapping
    public String showQuestionList(Model model) {
        model.addAttribute("question", qnaRepository.findAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String showOneQuestion(@PathVariable long id, Model model) {
        model.addAttribute("question", qnaRepository.findById(id).orElseThrow(NoQuestionException::new));
        return "qna/show";
    }

    @GetMapping("{id}/form")
    public String editQuestion(@PathVariable long id, Model model, HttpSession session) {
        Question question = qnaRepository.findById(id).orElseThrow(NoUserException::new);
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
    public String updateQuestion(@PathVariable long id, Question updateQuestion) {
        Question question = qnaRepository.findById(id).orElseThrow(NoUserException::new);
        question.update(updateQuestion);
        qnaRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable long id, HttpSession session) {
        Question question = qnaRepository.findById(id).orElseThrow(NoUserException::new);
        User sessionUser = HttpSessionUtils.getUserFromSession(session);

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        if (question.userConfirmation(sessionUser)) {
            throw new AccessDeniedException();
        }

        qnaRepository.delete(question);
        return "redirect:/";
    }

}
