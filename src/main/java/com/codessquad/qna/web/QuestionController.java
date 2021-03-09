package com.codessquad.qna.web;

import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
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

    Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/questions/form")
    public String getQuestionFormPage() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String submitQuestion(Question question, Model model) {
        questionRepository.save(question);
        model.addAttribute(question);

        logger.debug("question : {} ", question.toString());

        return "redirect:/";
    }

    @GetMapping("/")
    public String getQuestionListPage(Model model) {
        model.addAttribute("questionList", questionRepository.findAll());
        return "home";
    }

    @GetMapping("/questions/{index}")
    public String getQuestionDetail(@PathVariable(("index")) long index, Model model) {
        Question question = questionRepository.findById(index).get();
        model.addAttribute("question", question);

        logger.debug("question : {} ", question.toString());

        return "/qna/show";
    }
}
