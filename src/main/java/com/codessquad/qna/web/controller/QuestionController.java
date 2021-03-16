package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.question.QuestionRepository;
import com.codessquad.qna.web.domain.question.Question;
import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.dto.question.QuestionRequest;
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

    @PostMapping("/questions")
    public String create(QuestionRequest request, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value == null) {
            return "/users/login-form";
        }
        User user = (User) value;
        Question question = new Question(user.getName(), request.getTitle(), request.getContents());
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
