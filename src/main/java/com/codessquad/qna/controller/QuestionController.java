package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import com.github.jknack.handlebars.internal.lang3.ObjectUtils;
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

        if(!isValidQuestion(newQuestion)){
            return "/questions/form";
        }

        if (!questionRepository.save(newQuestion)) {
            return "/questions/form";
        }

        return "redirect:/";
    }

    private boolean isValidQuestion(Question question) {
        if (question == null){
            return false;
        }
        if ("".equals(question.getWriter()) || question.getWriter() == null) {
            return false;
        }
        if ("".equals(question.getTitle()) || question.getTitle() == null) {
            return false;
        }
        if ("".equals(question.getContents()) || question.getContents() == null) {
            return false;
        }

        return true;
    }

    @GetMapping("/{questionId}")
    public String showQuestionInDetail(@PathVariable(name = "questionId") int targetId, Model model) {

        model.addAttribute("question", questionRepository.getOne(targetId));

        return "/questions/show";
    }
}
