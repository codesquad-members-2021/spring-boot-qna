package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String show(Model model) {
        model.addAttribute("questions", questionService.findAllQuestion());
        return "index";
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
        questionService.create(title, contents, session);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ModelAndView viewQuestion(@PathVariable Long id) throws Exception {
        return getQuestionRepository("/qna/show", id);
    }

    @GetMapping("/{id}/confirm")
    public String confirmQuestion(@PathVariable Long id, HttpSession session, Model model) throws Exception {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        User loginUser = HttpSessionUtils.getSessionUser(session);
        Question question = questionService.findById(id);
        if (question.matchUser(loginUser)) {
            model.addAttribute("question", question);
            return "/qna/updateForm";
        }
        return "redirect:/";
    }

    @PutMapping("/{id}/update")
    public String updateQuestion(@PathVariable Long id, String title, String contents) throws Exception {
        questionService.update(id, title, contents);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}/delete")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) throws Exception {
        Question question = questionService.findById(id);
        User loginUser = HttpSessionUtils.getSessionUser(session);
        questionService.delete(question, id, loginUser);
        return "redirect:/";
    }

    private ModelAndView getQuestionRepository(String viewName, Long id) throws Exception {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("question", questionService.findById(id));
        modelAndView.addObject("answersList", questionService.findAnswers(id));
        modelAndView.addObject("answerCount", questionService.findAnswers(id).size());
        return modelAndView;
    }

}
