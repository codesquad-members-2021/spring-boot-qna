package com.codessquad.qna.web.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QnaController {
    private List<Question> questionList = new ArrayList<>();

    @PostMapping("/questions")
    public String createQuestion(String writerId, String title, String contents) {
        Question newQuestion = new Question(questionList.size() + 1, writerId, title, contents);
        questionList.add(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questionList);
        return "index";
    }

    @GetMapping("/questions/{questionId}")
    public String getOneQuestion(@PathVariable("questionId") int questionId, Model model) {
        Question foundQuestion = questionList.stream()
                .filter((question -> question.getId() == questionId))
                .findFirst().orElse(null);
        model.addAttribute("question", foundQuestion);
        return "qna/show";
    }

}
