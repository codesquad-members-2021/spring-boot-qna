package com.codessquad.qna.controller;

import com.codessquad.qna.exception.EntityNotFoundException;
import com.codessquad.qna.exception.InvalidSessionException;
import com.codessquad.qna.model.Question;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String qnaInputPage(HttpSession session) {
        getUserFromSession(session);
        return "qna/questionInputForm";
    }

    @PostMapping("/form")
    public String newQuestion(Question question, HttpSession session) {
        question.save(getUserFromSession(session));
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public String viewQuestion(@PathVariable Long questionId, Model model) {
        model.addAttribute("question", findByQuestionId(questionId));
        return "qna/questionDetail";
    }

    @GetMapping("/{questionId}/updateForm")
    public String qnaUpdatePage(@PathVariable Long questionId, Model model, HttpSession session) {
        Question question = findByQuestionId(questionId);
        checkSessionAndWriter(getUserFromSession(session), question);
        model.addAttribute("question", question);
        return "qna/questionUpdateForm";
    }

    @PutMapping("/{questionId}/updateForm")
    public String updateQuestion(@PathVariable Long questionId, Question updatedQuestion, HttpSession session) {
        Question question = findByQuestionId(questionId);
        checkSessionAndWriter(getUserFromSession(session), question);
        question.update(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(@PathVariable Long questionId, HttpSession session) {
        Question question = findByQuestionId(questionId);
        checkSessionAndWriter(getUserFromSession(session), question);
        questionRepository.delete(question);
        return "redirect:/";
    }

    private Question findByQuestionId(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new EntityNotFoundException("질문"));
    }

    private void checkSessionAndWriter(User sessionedUser, Question question) {
        if (!question.matchWriter(sessionedUser)) {
            throw new InvalidSessionException();
        }
    }
}
