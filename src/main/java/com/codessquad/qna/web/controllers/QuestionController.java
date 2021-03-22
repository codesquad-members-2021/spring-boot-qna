package com.codessquad.qna.web.controllers;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.service.AnswerService;
import com.codessquad.qna.web.service.QuestionService;
import com.codessquad.qna.web.utility.SessionUtility;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    private QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
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
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
        model.addAttribute("answers", answerService.findByQuestionId(id));
        return "qna/show";
    }

    @GetMapping("{id}/update")
    public String showUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionService.findById(id);
        User writer = question.getWriter();

        User sessionedUser = SessionUtility.findSessionedUser(session);
        SessionUtility.verifySessionUser(sessionedUser, writer, "본인이 작성한 글만 수정할 수 있습니다.");

        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("{id}")
    public String updateQuestion(@PathVariable Long id, String title, String contents) {
        Question question = questionService.findById(id);
        question.updateQuestion(title, contents);
        questionService.save(question);
        return "redirect:/";
    }

    @DeleteMapping("{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        Question question = questionService.findById(id);
        User writer = question.getWriter();

        User sessionedUser = SessionUtility.findSessionedUser(session);
        SessionUtility.verifySessionUser(sessionedUser, writer, "본인이 작성한 글만 삭제할 수 있습니다.");

        questionService.delete(question);
        return "redirect:/";
    }
}
