package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.SessionUtil.getLoginUser;

@Controller

@RequestMapping("/qna")
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String qnaPage() {
        return "redirect:/qna/list";
    }

    @GetMapping("/form")
    public String loginForm(HttpSession session) {
        questionService.isLogin(session);
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(Question question, HttpSession session) {
        questionService.createQuestion(question, getLoginUser(session));
        return "redirect:/qna/list";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("questionList", questionService.findAll());
        return "/qna/list";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        questionService.showDetailQuestion(id, model);
        //@Todo 모델에다가 데이터를 집어넣어주는건 컨트롤러 역할 같다. 개선할 수 있는 방법을 찾아보자
        return "/qna/show";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents) {
        questionService.updateQuestion(id, title, contents);
        return String.format("redirect:/qna/%d", id);
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
