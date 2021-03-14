package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class QnaController {
    private final QuestionRepository questionRepository;

    public QnaController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping("/questions")
    public String createQuestion(String writer, String title, String contents) {
        Question question = new Question(writer, title, contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String getDetailedQuestion(@PathVariable long id, Model model) {
        Optional<Question> question = questionRepository.findById(id);
        if (!question.isPresent()) {
            return "redirect:/";
        }
        model.addAttribute("question", question.get());
        return "qna/show";
    }
}
