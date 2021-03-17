package com.codessquad.qna.web.controllers;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.service.QuestionService;
import com.codessquad.qna.web.service.UserService;
import com.codessquad.qna.web.utility.SessionUtility;
import org.graalvm.compiler.lir.LIRInstruction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private QuestionService questionService;
    //private UserService userService;

    private QuestionController(QuestionService questionService) {
        this.questionService = questionService;
        //this.userService = userService;
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
        return "qna/show";
    }

    @GetMapping("{id}/update")
    public String clickUpdateQuestion(@PathVariable Long id, Model model, HttpSession session) {
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

}
