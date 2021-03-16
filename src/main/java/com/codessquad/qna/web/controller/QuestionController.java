package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.question.QuestionRepository;
import com.codessquad.qna.web.domain.question.Question;
import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.dto.question.QuestionRequest;
import com.codessquad.qna.web.exception.CRUDAuthenticationException;
import com.codessquad.qna.web.exception.QuestionNotFoundException;
import com.codessquad.qna.web.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping("/questions")
    public String create(QuestionRequest request, HttpSession session) {
        User user = SessionUtils.getLoginUser(session);
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
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        Question question = getQuestionById(id);
        User writer = question.getWriter();

        User user = SessionUtils.getLoginUser(session);

        if (!writer.isMatchingId(user.getId())) {
            throw new CRUDAuthenticationException("다른 사용자의 글을 수정할 수 없습니다.");
        }
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/questions/{id}/update")
    public String update(@PathVariable long id, QuestionRequest post) {
        Question question = getQuestionById(id);
        question.update(post.getTitle(), post.getContents());
        questionRepository.save(question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/questions/{id}/delete")
    public String delete(@PathVariable long id, HttpSession session) {
        Question question = getQuestionById(id);
        User user = SessionUtils.getLoginUser(session);
        if (!question.isMatchingWriter(user)) {
            throw new CRUDAuthenticationException("다른 사용자의 글을 삭제할 수 없습니다.");
        }
        questionRepository.delete(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String questionDetail(@PathVariable long id, Model model) {
        Question question = getQuestionById(id);
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    private Question getQuestionById(Long id){
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Cannot found question number "+ id));
    }

}
