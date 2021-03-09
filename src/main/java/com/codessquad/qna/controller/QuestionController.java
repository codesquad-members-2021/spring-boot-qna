package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    QuestionRepository questionRepository;

    @PostMapping
    public String createQuestion(Question newQuestion) {

        if (!questionRepository.save(newQuestion)){
            return "/questions/form";
        }

        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public String showQuestionInDetail(@PathVariable(name = "questionId") int targetId, Model model) {

        model.addAttribute("question", questionRepository.getOne(targetId));

        return "/questions/show";
    }
}
