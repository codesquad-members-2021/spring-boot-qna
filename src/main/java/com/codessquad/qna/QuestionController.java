package com.codessquad.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class QuestionController {

    private final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class.getName());

    private List<Question> questionList = new ArrayList<>();

    private List<Question> questionList() {
        return Collections.unmodifiableList(questionList);
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("questionList", questionList());
        return "index";
    }

    @GetMapping("/qna/form")
    public String qnaForm() {
        return "/qna/form";
    }

    @PostMapping("/qna/create")
    public String createQuestion(Question question, Model model) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        question.setDate(date);
        question.setIndex(questionList.size() + 1);
        questionList.add(question);
        model.addAttribute("questionList", questionList);

        LOGGER.info(question.toString());
        return "redirect:/";
    }

    @GetMapping("/qna/{index}")
    public String getQuestion(@PathVariable int index, Model model) {
        model.addAttribute("question", questionList.get(index - 1));
        return "/qna/show";
    }

}
