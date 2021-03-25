package com.codessquad.qna.controller;

import com.codessquad.qna.exception.EntryNotFoundException;
import com.codessquad.qna.exception.InvalidSessionException;
import com.codessquad.qna.repository.Question;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.repository.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{questionId}")
    public String viewQuestion(@PathVariable("questionId") Long questionId, Model model) {
        model.addAttribute("question", questionRepository.findById(questionId).orElseThrow(() -> new EntryNotFoundException("질문")));
        return "qna/questionDetail";
    }

    @GetMapping("/{questionId}/updateForm")
    public String qnaUpdatePage(@PathVariable("questionId") Long questionId, Model model, HttpSession session) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new EntryNotFoundException("질문"));
        if (!question.matchWriter(getUserFromSession(session))) {
            throw new InvalidSessionException();
        }
        model.addAttribute("question", question);
        return "qna/questionUpdateForm";
    }

    @PutMapping("/{questionId}/updateForm")
    public String updateQuestion(@PathVariable("questionId") Long questionId, Question updatedQuestion, HttpSession session) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new EntryNotFoundException("질문"));
        if (!question.matchWriter(getUserFromSession(session))) {
            throw new InvalidSessionException();
        }
        question.update(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/" + questionId;
    }
}
