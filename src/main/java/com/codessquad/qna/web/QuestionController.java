package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class.getName());

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String qnaForm(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/login";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String createQuestion(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/login";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionedUser.getUserId(), title, contents);
        questionRepository.save(newQuestion);

        return "redirect:/";
    }

    @GetMapping("{id}")
    public String getQuestion(@PathVariable Long id , Model model) {
        Question selectedQuestion = questionRepository.findById(id).get();
        if (id == selectedQuestion.getId()) {
            model.addAttribute("question", selectedQuestion);
        }

        return "/qna/show";
    }

}
