package com.codessquad.qna.web.controllers;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.service.QuestionService;
import com.codessquad.qna.web.utility.SessionUtility;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

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
    public String showQuestion(@PathVariable Long id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
        model.addAttribute("answers", questionService.findAnswersByQuestionId(id));
        return "qna/show";
    }

    @GetMapping("{id}/update")
    public String showUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionService.findById(id);
        User sessionedUser = SessionUtility.findSessionedUser(session);
        questionService.verifyQuestionWriter(question, sessionedUser);
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("{id}")
    public String updateQuestion(@PathVariable Long id, String title, String contents) {
        Question question = questionService.findById(id);
        question.update(title, contents);
        questionService.save(question);
        return "redirect:/";
    }

    @DeleteMapping("{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        Question question = questionService.findById(id);
        User sessionedUser = SessionUtility.findSessionedUser(session);
        questionService.verifyQuestionWriter(question, sessionedUser);
        questionService.delete(question);
        return "redirect:/";
    }
}
