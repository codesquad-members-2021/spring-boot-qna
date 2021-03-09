package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QnaController {
    private List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String createQuestion(String writer, String title, String contents) {
        Question question = new Question(writer, title, contents, questions.size() + 1);
        System.out.println(question);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String getDetailedQuestion(@PathVariable long id, Model model) {
        model.addAttribute("question", questions.get((int) (id - 1)));
        return "qna/show";
    }
}
