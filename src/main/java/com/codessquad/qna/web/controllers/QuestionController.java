package com.codessquad.qna.web.controllers;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.service.QuestionService;
import com.codessquad.qna.web.utility.SessionUtility;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private QuestionService questionService;

    private QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/form")
    public String askQuestion(HttpSession session) {
        User writer = SessionUtility.findSessionedUser(session);
        return "qna/form";
    }

    @PostMapping
    public String saveQuestionForm(String title, String contents, HttpSession session) {
        User writer = SessionUtility.findSessionedUser(session);
        Question question = new Question(writer, title, contents);
        questionService.save(question);
        return "redirect:/";
    }

    @GetMapping
    public String viewAllQuestions(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable("id") Long id, Model model) {
        Question question = questionService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 id의 질문이 존재하지 않습니다."));
        model.addAttribute("question", question);
        return "qna/show";
    }

}
