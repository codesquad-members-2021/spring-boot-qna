package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.question.QuestionRepository;
import com.codessquad.qna.web.domain.question.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // TODO : 현재 로그인한 유저의 name이 question의 writer 값으로
    @PostMapping("/questions")
    public String create(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/form")
    public String getForm(HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value == null) {
            return "redirect:/users/login-form";
        }
        return "qna/form";
    }

    @GetMapping("/questions/{id}")
    public String getQuestionDetail(@PathVariable long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

}
