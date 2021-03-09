package com.codessquad.qna.Controller;

import com.codessquad.qna.domain.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    final private List<Question> qList = new ArrayList<>();


    @GetMapping("/qna/form.html")
    public String questionList() {
        logger.info("askQuestion");
        return "qna/form";
    }

    @PostMapping("/questions")
    public String askQuestion() {
        return "";
    }


}
