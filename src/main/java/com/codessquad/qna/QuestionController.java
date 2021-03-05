package com.codessquad.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<Question> questionList = new ArrayList<>();

    @PostMapping("/questions")
    private String questions(Question question){
        logger.info(question.toString());
        questionList.add(question);
        return "redirect:/";
    }
}
