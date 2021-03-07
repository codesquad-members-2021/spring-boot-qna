package com.codessquad.qna.question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("questions")
public class QuestionController {
    private List<Question> questions = Collections.synchronizedList(new ArrayList<>(Question.getDummyData()));

    @GetMapping
    public ModelAndView getQuestions() {
        return new ModelAndView("/qna/list", "questions", questions);
    }

    @GetMapping("/{id}")
    public ModelAndView getQuestion(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("/qna/show");
        modelAndView.addObject("question", questions.get(id));
        modelAndView.addObject("id", id);

        return modelAndView;
    }

    @PostMapping
    public String createQuestions(Question question) {
        questions.add(question);
        return "redirect:/questions";
    }
}
