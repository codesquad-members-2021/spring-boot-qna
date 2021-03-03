package com.codessquad.qna.controller;

import com.codessquad.qna.question.Question;
import com.codessquad.qna.question.QuestionRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private final List<Question> questions = new ArrayList<>();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @PostMapping("/questions")
    public String query(QuestionRequest questionRequest) {
        Question question = new Question(questionRequest);
        question.setId(questions.size() + 1);
        question.setTime(ZonedDateTime.now(ZoneId.of("Asia/Seoul")));
        question.setPoint(0);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String qnaShow(@PathVariable("id") String id, Model model) {
        if (!id.matches("[1-9]\\d*")) {
            return "redirect:/";
        }
        int questionIndex = Integer.parseInt(id) - 1;
        if (questionIndex < questions.size()) {
            Question question = questions.get(questionIndex);
            model.addAttribute(question);
            return "qna/show";
        }
        return "redirect:/";
    }

}
