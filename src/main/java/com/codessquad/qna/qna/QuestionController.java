package com.codessquad.qna.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String create(Question question) {
        question.setIndex(questions.size() + 1 + "");
        questions.add(question);
        System.out.println(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/questions/{index}")
    public String showQuestion(@PathVariable String index, Model model) {
        for(Question question : questions){
            if(questions.indexOf(question) == Integer.parseInt(index) - 1){
                model.addAttribute("question", question);
                return "qna/show";
            }
        }
        return "redirect:/";
    }


}
