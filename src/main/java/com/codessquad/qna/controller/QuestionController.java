package com.codessquad.qna.controller;

import com.codessquad.qna.repository.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private static String dateTime = "yyyy-MM-dd HH:mm";

    private List<Question> questions = new ArrayList<>();

    @GetMapping
    public String showQuestions(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @PostMapping
    public String makeNewQuestion(Question question) {
        SimpleDateFormat format = new SimpleDateFormat(dateTime);
        Date date = new Date();
        String formatTime = format.format(date);
        question.setDateTime(formatTime);
        question.setIndex(questions.size() + 1);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String toQnaForm() {
        return "questionInputForm";
    }

    @GetMapping("/{index}")
    public String showQuestion(@PathVariable("index") int index, Model model) {
        Question question = questions.get(index - 1);
        model.addAttribute("question", question);
        return "questionDetail";
    }
}
