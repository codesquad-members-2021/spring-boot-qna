package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions")
    private String questions(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    private ModelAndView showQuestionContents(@PathVariable("id") Long targetId) {
        Objects.requireNonNull(targetId, "Exception: targetKey가 NULL 값입니다.");

        Optional<Question> questionOptional = questionRepository.findById(targetId);
        Question questionData = questionOptional.orElseThrow(NoSuchElementException::new);

        ModelAndView mav = new ModelAndView("qna/show");
        mav.addObject("question", questionData);

        return mav;
    }


}
