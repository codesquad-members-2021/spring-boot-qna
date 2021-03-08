package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final Pattern questionIdPattern = Pattern.compile("[1-9]\\d*");

    @Autowired
    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        if (!HttpSessionUtils.isLogined(session)) {
            return "redirect:/users/login";
        }
        return "qna/form";
    }

    @PostMapping()
    public String query(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLogined(session)) {
            return "redirect:/users/login";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        question.setWriter(sessionUser.getName());
        question.setTime(LocalDateTime.now());
        question.setPoint(0);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ModelAndView qnaShow(@PathVariable("id") String id) {
        Matcher questionIdMatcher = questionIdPattern.matcher(id);
        if (!questionIdMatcher.matches()) {
            return new ModelAndView("redirect:/");
        }
        Optional<Question> questionOptional = questionRepository.findById(Long.parseLong(id));
        if (questionOptional.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("qna/show");
            modelAndView.addObject(questionOptional.get());
            return modelAndView;
        }
        return new ModelAndView("redirect:/");
    }

}
