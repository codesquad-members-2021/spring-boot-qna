package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Controller
public class QuestionController {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping("/questions")
    private String questions(Question question) {
        Objects.requireNonNull(question, "Exception: question이 NULL 값입니다.");

        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    private String showQuestionContents(@PathVariable("id") Long targetId, Model model) {
        Objects.requireNonNull(targetId, "Exception: targetKey가 NULL 값입니다.");

        Optional<Question> questionOptional = questionRepository.findById(targetId);
        Question questionData = questionOptional.orElseThrow(NoSuchElementException::new);

        model.addAttribute("question", questionData);

        return "qna/show";
    }


}
