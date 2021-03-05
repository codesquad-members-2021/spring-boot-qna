package com.codessquad.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<Question> questionList = new ArrayList<>();

    @PostMapping("/questions")
    private String questions(Question question, Model model){
        logger.info(question.toString());
        question.setId(questionList.size()+1); // 질문 객체 아이디 정의
        questionList.add(question);
        model.addAttribute("questions",questionList);
        return "redirect:/";
    }
    @GetMapping("/")
    private String questionsList(Model model){
        model.addAttribute("questions",questionList);
        return "/index";
    }

}
