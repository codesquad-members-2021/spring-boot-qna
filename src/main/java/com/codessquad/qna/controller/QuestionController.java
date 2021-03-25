package com.codessquad.qna.controller;

import com.codessquad.qna.exception.EntryNotFoundException;
import com.codessquad.qna.exception.InvalidSessionException;
import com.codessquad.qna.repository.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.controller.HttpSessionUtils.getUserFromSession;
import static com.codessquad.qna.controller.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String qnaInputPage(HttpSession session) {
        if (!isLoginUser(session)) {
            throw new InvalidSessionException();
        }
        return "qna/questionInputForm";
    }

    @PostMapping("/form")
    public String newQuestion(Question question, HttpSession session) {
        question.save(getUserFromSession(session));
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String viewQuestion(@PathVariable("id") Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("질문")));
        return "qna/questionDetail";
    }
}
