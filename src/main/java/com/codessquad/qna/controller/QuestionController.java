package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class QuestionController {

    Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private static List<Question> questions = new ArrayList<>();

    public static List<Question> questions() {
        return Collections.unmodifiableList(questions);
    }

    @GetMapping("questions/form")
    public String getQuestionForm() {
        return "/qna/form";
    }

    @PostMapping("questions/ask")
    public String askQuestion(Question question) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format_time = format.format(System.currentTimeMillis());

        question.setDate(format_time);
        question.setIndex(questions.size() + 1);

        questions.add(question);
        logger.info(question.toString());

        return "redirect:/";
    }

    @GetMapping("questions/{index}")
    public String showQuestion(@PathVariable("index") long index, Model model) {
        model.addAttribute("question", questions.get((int) index - 1));
        return "/qna/show";
    }

}
