package com.codessquad.qna.Controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepostory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepostory questionRepostory;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/qna/form")
    public String questionList() {
        logger.info("askQuestion");
        return "qna/form";
    }

    @PostMapping("/qna/questions")
    public String askQuestion(Question question) {
        questionRepostory.save(question);
        return "redirect:/qna/list";
    }

    @GetMapping("/qna/list")
    public String showQuestionList(Model model) {
        model.addAttribute("questionList",questionRepostory.findAll());
        return "qna/list";
    }

    @GetMapping("/qna/{index}")
    public String showProfile(@PathVariable Long id, Model model) {
        Question currentQuestion = questionRepostory.findById(id).get();
        model.addAttribute("question",currentQuestion);
        logger.info("update Question : " + currentQuestion.toString());
        return "/qna/show";
    }

}
