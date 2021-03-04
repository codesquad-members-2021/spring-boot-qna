package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        questionList.add(question);
        model.addAttribute("questionList", questionList);
        return "redirect:/";
    }

}
