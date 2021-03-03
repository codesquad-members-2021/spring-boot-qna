package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    List<Question> questionList = new ArrayList<>();

    @GetMapping("/questions/form")
    public String form() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String submitQuestion(String writer, String title, String contents, Model model) {
        long id = questionList.size() + 1;
        Question question = new Question(id, writer, title, contents);

        model.addAttribute(question);
        questionList.add(question);
        System.out.println(question);

        return "redirect:/";
    }

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("questionList", questionList);
        return "home";
    }

    @GetMapping("/questions/{index}")
    public String getQuestionDetail(@PathVariable(("index")) long index, Model model) {
        model.addAttribute("question", questionList.get((int) index - 1));
        return "/qna/show";
    }
}
