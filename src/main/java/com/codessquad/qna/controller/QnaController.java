package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class QnaController {

    private static final Logger logger = LoggerFactory.getLogger(QnaController.class);

    private List<Question> questions = new ArrayList<>();

    @PostMapping
    public String createQuestion(Question question) {
        questions.add(question);
        question.setId(questions.size());
        question.setWriteTime(new SimpleDateFormat(("yyyy-MM-dd HH:mm")).format(new Date()));
        logger.info(question.getWriteTime());
        logger.info("question.getQuestionId() = " + question.getId());
        return "redirect:/questions";
    }

    @GetMapping
    public String loadHome(Model model) {
        model.addAttribute("questions", questions);
        logger.info("questions = " + questions);
        return "index";
    }

    @GetMapping("/{questionId}")
    public String showQuestion(@PathVariable int questionId, Model model) {
        Question question = questions.get(questionId - 1);
        model.addAttribute("question", question);
        return "/qna/show";
    }

}
