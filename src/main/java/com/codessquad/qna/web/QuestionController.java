package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping("/questions")
    public String create(Question question, HttpSession session) {
        question.setPostTime();
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(NoSuchElementException::new));
        return "qna/show";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleException() {
        return "exceptionHandle";
    }
}
