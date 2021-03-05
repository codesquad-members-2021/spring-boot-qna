package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        System.out.println(question.toString());
        question.setIndex(questionList.size() + 1);
        questionList.add(question);
        model.addAttribute("questionList", questionList);
        return "redirect:/";
    }

    @GetMapping("/qna/{index}")
    public String getQuestion(@PathVariable int index, Model model) {
        model.addAttribute("question", questionList.get(index - 1));
        return "/qna/show";
    }

}
