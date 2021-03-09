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
    QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        Question newQuestion = new Question();

        newQuestion.setWriter(question.getWriter());
        newQuestion.setTitle(question.getTitle());
        newQuestion.setContents(question.getContents());

        questionRepository.save(newQuestion);

        return "redirect:/";
    }

    @GetMapping("/questions/{questionId}")
    public String createQuestionInDetail(@PathVariable(name = "questionId") int targetId, Model model) {
        Question targetQuestion = questionRepository.getOne(targetId);

        model.addAttribute("question", targetQuestion);

        return "/questions/show";
    }
}
