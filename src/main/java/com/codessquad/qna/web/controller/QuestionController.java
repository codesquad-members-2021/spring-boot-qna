package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.HttpSessionUtils;
import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/form")
    public String getQuestionForm(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping
    public String createQuestion(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        questionService.postQuestion(question, HttpSessionUtils.getSessionedUser(session));
        return "redirect:/questions";
    }

    @GetMapping
    public String getQuestions(Model model) {
        model.addAttribute("questions", questionService.findQuestions());
        return "/index";
}

    @GetMapping("/{id}")
    public String getQuestion(@PathVariable long id, Model model) {
        try {
            model.addAttribute("question", questionService.findQuestion(id));
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable long id, HttpSession session, Model model) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        //try-catch 할까 말까  //여기 세션 들어가는 곳 원래 자바지기 때로 controller로 빼야 한다.
        model.addAttribute("question", questionService.getOriginQuestion(id, session));
        return "/qna/updateForm";
    }

    @PutMapping ("/{id}")
    public String updateQuestion(@PathVariable long id, Question question, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        //여기 세션 들어가는 곳 원래 자바지기 때로 controller로 빼야 한다.
        questionService.updateQuestion(questionService.getOriginQuestion(id, session), question);
        return "redirect:/questions/";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        //seession에 저장되어 있는 id랑 / id인자로 받은 글쓴이랑 같은 확인
        if(!questionService.findQuestion(id).getWriter().equals(HttpSessionUtils.getSessionedUser(session).getUserId())){
            throw new IllegalStateException("자신이 올린 질문만 삭제할 수 있습니다");
        }
       questionService.deleteQuestion(id);
        return "redirect:/questions";
    }
}
