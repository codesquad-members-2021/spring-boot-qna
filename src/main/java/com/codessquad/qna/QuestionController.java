package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class QuestionController {

    private static List<Question> questionList = new ArrayList<>();
    public static List<Question> questionList() {
        return Collections.unmodifiableList(questionList);
    }

    @PostMapping("/qna/create")
    public String question(Question question, Model model) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        question.setDate(date);
        question.setIndex(questionList.size() + 1);
        questionList.add(question);
        model.addAttribute("questionList", questionList);

        System.out.println(question.toString());
        return "redirect:/";
    }

    @GetMapping("/qna/{index}")
    public String getQuestion(@PathVariable int index, Model model) {
        model.addAttribute("question", questionList.get(index - 1));
        return "/qna/show";
    }

}
