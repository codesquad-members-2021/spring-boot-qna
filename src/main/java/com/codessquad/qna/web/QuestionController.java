package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private List<Question> questionList = new ArrayList<>();

    @GetMapping("/questions/form")
    public String getQuestionFormPage() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String submitQuestion(String writer, String title, String contents, Model model) {
        long id = questionList.size() + 1;
        Question question = new Question(id, writer, title, contents);

        model.addAttribute(question);
        questionList.add(question);

        logger.info(question.toString());

        return "redirect:/";
    }

    @GetMapping("/")
    public String getQuestionListPage(Model model) {
        model.addAttribute("questionList", questionList);
        return "home";
    }

    @GetMapping("/questions/{index}")
    public String getQuestionDetail(@PathVariable(("index")) long index, Model model) {
        Question question = questionList.get((int) index - 1);
        model.addAttribute("question", question);

        logger.info(question.toString());

        return "/qna/show";
    }
}
