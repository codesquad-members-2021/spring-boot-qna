package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.codessquad.qna.valid.QuestionValidation;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/form")
    public String createQuestion(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        return "/qna/form";
    }

    @PostMapping("/form")
    public String createQuestion(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/login";
        }
        User loginUser = HttpSessionUtils.getSessionUser(session);
        Question question = new Question(loginUser, title, contents);
        QuestionValidation.validQuestion(question);
        logger.info("question {}. ", question);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ModelAndView viewQuestion(@PathVariable Long id) {
        return getQuestionRepository("/qna/show", id);
    }

    @GetMapping("/{id}/confirm")
    public String confirmQuestion(@PathVariable Long id, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        User loginUser = HttpSessionUtils.getSessionUser(session);
        Question question = questionRepository.findById(id).orElse(null);
        if (question.matchUser(loginUser)) {
            model.addAttribute("question", question);
            return "/qna/updateForm";
        }
        return "redirect:/";
    }

    @PutMapping("/{id}/update")
    public String updateQuestion(@PathVariable Long id, String title, String contents) {
        Question question = questionRepository.findById(id).orElse(null);
        question.updateQuestion(title, contents);
        QuestionValidation.validQuestion(question);
        logger.info("question {}. ", question);
        questionRepository.save(question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}/delete")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElse(null);
        User loginUser = HttpSessionUtils.getSessionUser(session);
        if (question.matchUser(loginUser)) {
            questionRepository.delete(question);
            return "redirect:/";
        }
        return "redirect:/";
    }

    private ModelAndView getQuestionRepository(String viewName, Long id) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("question", questionRepository.findById(id).orElse(null));
        return modelAndView;
    }

}
