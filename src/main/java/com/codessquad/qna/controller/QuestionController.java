package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.exception.NotLoggedInException;
import com.codessquad.qna.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String list(@RequestParam(name = "page", defaultValue = "1") int pageNumber, Model model) {
        Page<Question> page = questionService.questionsPage(pageNumber);
        logger.debug("total : " + page.getTotalElements());
        logger.debug("total page : " + page.getTotalPages());
        model.addAttribute("questions", page);
        return "qna/list";
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoggedIn(session)) {
            throw new NotLoggedInException();
        }
        return "qna/form";
    }

    @PostMapping
    public String create(Question question, HttpSession session) {
        questionService.register(question, HttpSessionUtils.loginUser(session));
        return "redirect:/questions";
    }

    @GetMapping("/{questionId}")
    public String show(@PathVariable("questionId") Long id, Model model) {
        model.addAttribute("question", questionService.question(id));
        return "qna/show";
    }

    @GetMapping("/{questionId}/form")
    public String updateForm(@PathVariable("questionId") Long id, HttpSession session, Model model) {
        model.addAttribute("question",
                questionService.questionWithAuthentication(id, HttpSessionUtils.loginUser(session)));
        return "/qna/updateForm";
    }

    @PutMapping("/{questionId}")
    public String update(@PathVariable("questionId") Long id, Question updatingQuestion, HttpSession session) {
        questionService.update(id, HttpSessionUtils.loginUser(session), updatingQuestion);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        questionService.delete(id, HttpSessionUtils.loginUser(session));
        return "redirect:/questions";
    }

}
