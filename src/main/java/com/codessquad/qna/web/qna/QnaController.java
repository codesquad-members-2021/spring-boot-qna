package com.codessquad.qna.web.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QnaController {
    Logger logger = LoggerFactory.getLogger(QnaController.class);
    private List<Question> questionList = new ArrayList<>();

    @PostMapping("/questions")
    public String onRequestCreateQuestion(String writer, String title, String contents) {
        logger.info("onRequestCreateQuestion called");
        Question newQuestion = new Question(writer, title, contents);
        questionList.add(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/")
    public String onRequestShowQuestions(Model model) {
        logger.info("onRequestShowQuestions called");
        model.addAttribute("questions", questionList);
        return "index";
    }
}
