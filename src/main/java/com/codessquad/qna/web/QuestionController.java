package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping("/questions")
    public String create(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        question.setWriter(HttpSessionUtils.getUserFromSession(session));
        question.setPostTime();
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(NoSuchElementException::new));
        return "qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String update(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isQuestionWriter(sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/questions/{id}")
    public String updateForm(@PathVariable Long id, Question updatedQuestion, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isQuestionWriter(sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        question.update(updatedQuestion);
        questionRepository.save(updatedQuestion);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/questions/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session){
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isQuestionWriter(sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        questionRepository.delete(question);
        return "redirect:/";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException() {
        return "noSuchElementExceptionHandle";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException() {
        return "unableToAccessToOthers";
    }
}
