package com.codessquad.qna.question.ui;

import com.codessquad.qna.question.application.QuestionService;
import com.codessquad.qna.question.dto.QuestionRequest;
import com.codessquad.qna.user.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.codessquad.qna.common.HttpSessionUtils.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public String createQuestion(@Valid QuestionRequest questionRequest, HttpSession session) {
        User writer = getUserAttribute(session);
        questionService.save(questionRequest, writer);
        return "redirect:/questions";
    }

    @GetMapping("form")
    public String getQuestionForm(HttpSession session) {
        checkLoggedIn(session);
        return "question/form";
    }

    @GetMapping
    public String getQuestions(Model model) {
        model.addAttribute("questions", questionService.getList());
        return "question/list";
    }

    @GetMapping("{id}")
    public String getQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.get(id));
        return "question/show";
    }

    @GetMapping("{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.get(id));
        return "question/updateForm";
    }

    @PutMapping("{id}")
    public String updateQuestion(@PathVariable Long id, @Valid QuestionRequest questionRequest, HttpSession session) {
        checkQuestionAuthorization(id, session);
        User writer = getUserAttribute(session);
        questionService.update(id, questionRequest, writer);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        checkQuestionAuthorization(id, session);
        questionService.delete(id);
        return String.format("redirect:/questions", id);
    }

    private void checkQuestionAuthorization(Long id, HttpSession session) {
        User writer = questionService.getWriter(id);
        checkAuthorization(writer, session);
    }
}
