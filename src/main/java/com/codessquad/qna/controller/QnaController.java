package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QnaController {

    private QuestionRepository questionRepository;

    public QnaController (QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/form")
    public String showQuestionForm(HttpSession session) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping
    public String createQuestion(HttpSession session, String title, String contents) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }
        Question question = new Question(sessionedUser.getUserId(), title, contents);
        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping
    public String readQuestions(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }

        Question question = questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 질문이 존재하지 않습니다."));
        model.addAttribute("question", question);

        return "/qna/update";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, HttpSession session, Question newQuestion) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 질문이 존재하지 않습니다."));
        question.update(newQuestion);
        questionRepository.save(question);
        return "redirect:/questions";

    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 질문이 존재하지 않습니다."));
        questionRepository.delete(question);
        return "redirect:/questions";
    }

}
