package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    private String showQuestionContents(@PathVariable("id") Long targetId, Model model) {
        //TODO. model.addAttribute("invalidMember", true);
        model.addAttribute("question",questionRepository.findById(targetId).get());
        return "qna/show";
    }


}
