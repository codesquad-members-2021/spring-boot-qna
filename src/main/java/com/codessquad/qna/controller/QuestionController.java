package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String viewQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public ModelAndView viewQuestion(@PathVariable Long id) {
        return getQuestionRepository("/qna/show", id);
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    private ModelAndView getQuestionRepository(String viewName, Long id) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("question", questionRepository.findById(id).orElse(null));
        return modelAndView;
    }

}
