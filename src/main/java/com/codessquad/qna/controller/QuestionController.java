package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.exception.UnauthorizedException;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.SessionUtil.getLoginUser;
import static com.codessquad.qna.utils.SessionUtil.isLoginUser;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String showList(Model model) {
        model.addAttribute("questionList", questionService.findAll());
        return "/qna/list";
    }

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        if (!isLoginUser(session)) {
            throw new UnauthorizedException("질문글 작성을 위해서는 로그인이 필요합니다");
        }
        return "/qna/form";
    }

    @PostMapping
    public String create(Question question, HttpSession session) {
        questionService.createQuestion(question, getLoginUser(session));
        return "redirect:/qna/list";
    }


    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.showDetailQuestion(id));
        return "/qna/show";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents) {
        questionService.updateQuestion(id, title, contents);
        return ("redirect:/questions/" + id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        questionService.deleteQuestion(id, session);
        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        questionService.updateForm(id, model, session);
        return "/qna/updateForm";
    }
}
