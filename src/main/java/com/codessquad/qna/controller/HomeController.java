package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final QuestionRepository questionRepository;

    @Autowired
    public HomeController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/")
    private String getQuestionsList(Model model) {
        Iterable<Question> originQuestions = questionRepository.findAll();
        List<Question> questions = new ArrayList<>();

        for(Question q : originQuestions){
            if(!q.isDeleted()){  // soft delete
                questions.add(q);
            }
        }
        model.addAttribute("questions", questions);
        return "index";
    }
}
