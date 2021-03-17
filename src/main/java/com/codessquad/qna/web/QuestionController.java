package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.QuestionRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.web.HttpSessionUtils.*;

@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping("/questions")
    public String create(Question question, HttpSession session) {
        if (!isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        question.setWriter(getUserFromSession(session));
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
    public String show(@PathVariable Long id, Model model) throws NotFoundException {
        // Todo: 왜안돼
        Question question = questionRepository.findById(id).orElseThrow(() -> new NotFoundException("게시물이 존재하지 않습니다."));
        model.addAttribute("question", question);
        return "qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String update(@PathVariable Long id, Model model, HttpSession session) throws NotFoundException {
        if (!isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        Question question = questionRepository.findById(id).orElseThrow(() -> new NotFoundException("게시물이 존재하지 않습니다."));
        User sessionedUser = getUserFromSession(session);
        if (!question.isQuestionWriter(sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/questions/{id}")
    public String updateForm(@PathVariable Long id, Question updatedQuestion, HttpSession session) throws NotFoundException {
        if (!isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        Question question = questionRepository.findById(id).orElseThrow(() -> new NotFoundException("게시물이 존재하지 않습니다."));
        User sessionedUser = getUserFromSession(session);
        if (!question.isQuestionWriter(sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        question.update(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/questions/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) throws NotFoundException {
        if (!isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        Question question = questionRepository.findById(id).orElseThrow(() -> new NotFoundException("게시물이 존재하지 않습니다."));
        User sessionedUser = getUserFromSession(session);
        if (!question.isQuestionWriter(sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        questionRepository.delete(question);
        return "redirect:/";
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFountException() {
        return "notFoundExceptionHandle";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException() {
        return "unableToAccessToOthers";
    }
}
