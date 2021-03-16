package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.question.QuestionRepository;
import com.codessquad.qna.web.domain.question.Question;
import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.dto.question.QuestionRequest;
import com.codessquad.qna.web.utils.SessionUtils;
import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping("/questions")
    public String create(QuestionRequest request, HttpSession session) throws NotFoundException {
        if (!SessionUtils.isLoginUser(session)) {
            return "redirect:/users/login-form";
        }
        User user = SessionUtils.getLoginUser(session)
                .orElseThrow(() -> new NotFoundException("No login user"));
        Question question = new Question(user, request.getTitle(), request.getContents());
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/form")
    public String questionForm(HttpSession session) {
        if (!SessionUtils.isLoginUser(session)) {
            return "/users/login-form";
        }
        return "qna/form";
    }

    @GetMapping("/questions/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) throws NotFoundException {
        Question question = questionRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        User writer = question.getWriter();

        if (!SessionUtils.isLoginUser(session)) {
            return "/users/login-form";
        }

        User user = SessionUtils.getLoginUser(session).orElseThrow(() -> new NotFoundException("No login user"));

        if (!writer.isMatchingId(user.getId())) {
            throw new IllegalStateException("다른 사용자의 글을 수정할 수 없습니다.");
        }
        model.addAttribute("id", id);
        return "redirect:/qna/updateForm";
    }


    @GetMapping("/questions/{id}")
    public String questionDetail(@PathVariable long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

}
