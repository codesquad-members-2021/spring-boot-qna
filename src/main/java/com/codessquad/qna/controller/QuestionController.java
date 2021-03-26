package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.dto.UserDto;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String questionList(@PageableDefault(size = 15) Pageable pageable, Model model) {
        model.addAttribute("Questions", questionService.findQuestionList(pageable));
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("pre", pageable.next().getPageNumber() - 2);
        return "index";
    }


    @PostMapping
    public String createQuestion(Question question, HttpSession session) {
        UserDto user = HttpSessionUtils.getUserFromSession(session);
        questionService.write(question, user);
        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public String renderQuestion(@PathVariable Long questionId, Model model) {
        Question findQuestion = questionService.findById(questionId);

        model.addAttribute("question", findQuestion);
        return "qna/show";
    }

    @GetMapping("/form")
    public String renderQuestionForm(HttpSession session) {
        HttpSessionUtils.getUserFromSession(session);
        return "qna/form";
    }

    @GetMapping("/{id}/form")
    public String renderUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        Question findQuestion = questionService.findById(id);
        UserDto user = HttpSessionUtils.getUserFromSession(session);
        findQuestion.checkSameUser(user.getId());
        model.addAttribute("question", findQuestion);
        return "qna/updateForm";

    }

    @PutMapping("/{id}")
    public String questionUpdate(@PathVariable Long id, Question updateQuestion, HttpSession session) {
        UserDto user = HttpSessionUtils.getUserFromSession(session);
        questionService.update(id, updateQuestion, user);
        return "redirect:/questions/" + id;

    }

    @DeleteMapping("/{id}")
    public String questionDelete(@PathVariable Long id, HttpSession session) {
        UserDto user = HttpSessionUtils.getUserFromSession(session);
        questionService.delete(id, user);
        return "redirect:/";

    }

}
