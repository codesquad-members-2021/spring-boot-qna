package com.codessquad.qna.Controller;

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

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    final private List<Question> questionList = new ArrayList<>();

    @GetMapping("/qna/form")
    public String questionList() {
        logger.info("askQuestion");
        return "qna/form";
    }

    @PostMapping("/qna/questions")
    public String askQuestion(Question question) {
        //qList.add();
        question.setIndex(questionList.size() + 1);
        questionList.add(question);
        return "redirect:/qna/list";
    }
    @GetMapping("/qna/list")
    public String showQuestionList(Model model) {
        model.addAttribute("questionList",questionList);
        return "qna/list";
    }

    @GetMapping("/qna/{index}")
    public String showProfile(@PathVariable int index, Model model) {
        Question currentQuestion = questionList.get(index - 1);
        model.addAttribute("question",currentQuestion);
        logger.info("update Question : " + currentQuestion.toString());
        return "/qna/show";
    }


}
