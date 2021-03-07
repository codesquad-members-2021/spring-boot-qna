package com.codessquad.qna.web.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    QuestionRepository questionRepository = new QuestionRepository();

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String question_create(Question question) {
        questionRepository.add(question);
        return "redirect:/questions";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String question_list(Model model) {
        model.addAttribute("questions", questionRepository.getQuestions());
        return "question/list";
    }

    @RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
    public String question_profile(@PathVariable("questionId") int questionId, Model model) {
        model.addAttribute("question", questionRepository.getQuestion(questionId));
        return "question/show";
    }
}
