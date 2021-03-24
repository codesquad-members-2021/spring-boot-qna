package com.codessquad.qna.question.ui;

import com.codessquad.qna.question.application.QuestionService;
import com.codessquad.qna.question.dto.QuestionRequest;
import com.codessquad.qna.user.domain.User;
import org.springframework.data.domain.Pageable;
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
    public String create(@Valid QuestionRequest questionRequest, HttpSession session) {
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
    public String getList(Pageable pageable, Model model) {
        model.addAttribute("questions", questionService.getList(pageable));
        return "question/list";
    }

    @GetMapping("{id}")
    public String get(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.get(id));
        return "question/show";
    }

    @GetMapping("{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.get(id));
        return "question/updateForm";
    }

    @PutMapping("{id}")
    public String update(@PathVariable Long id, @Valid QuestionRequest questionRequest, HttpSession session) {
        checkQuestionAuthorization(id, session);
        User writer = getUserAttribute(session);
        questionService.update(id, questionRequest, writer);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        checkQuestionAuthorization(id, session);
        questionService.delete(id);
        return String.format("redirect:/questions", id);
    }

    private void checkQuestionAuthorization(Long id, HttpSession session) {
        User writer = questionService.getWriter(id);
        checkAuthorization(writer, session);
    }
}
