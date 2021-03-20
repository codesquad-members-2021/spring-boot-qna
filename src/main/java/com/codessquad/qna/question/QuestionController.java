package com.codessquad.qna.question;

import com.codessquad.qna.utils.SessionUtils;
import org.springframework.stereotype.Controller;
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
    public ModelAndView readAll() {
        return new ModelAndView("/qna/list", "questions", questionService.readAll());
    }

    @PostMapping
    public String create(QuestionDTO question, HttpSession session) {
        question.setWriter(SessionUtils.getSessionUser(session));

        questionService.create(question);

        return "redirect:/questions";
    }

    @GetMapping("/{id}")
    public ModelAndView read(@PathVariable Long id) {
        return new ModelAndView("/qna/show", "question", questionService.read(id));
    }

    @GetMapping("/{id}/form")
    public ModelAndView viewUpdateForm(@PathVariable Long id, HttpSession session) {
        Question result = questionService.readVerifiedQuestion(id, SessionUtils.getSessionUser(session)).toEntity();

        return new ModelAndView("/qna/updateForm", "question", result);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, QuestionDTO newQuestion, HttpSession session) {
        newQuestion.setWriter(SessionUtils.getSessionUser(session));

        questionService.update(id, newQuestion);

        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        questionService.delete(id, SessionUtils.getSessionUser(session));

        return "redirect:/questions";
    }
}
