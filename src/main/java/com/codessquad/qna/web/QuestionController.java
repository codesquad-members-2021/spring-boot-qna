package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NoQuestionException;
import com.codessquad.qna.exception.NoUserException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/question")
public class QuestionController {
    private final QuestionRepository qnaRepository;

    public QuestionController(QuestionRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        return "qna/form";
    }

    @PostMapping
    public String createNewQna(Question qna, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        assert sessionUser != null;
        qna.setWriter(sessionUser);

        qnaRepository.save(qna);
        return "redirect:/";
    }

    @GetMapping
    public String qnaList(Model model) {
        model.addAttribute("question", qnaRepository.findAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String showOneQuestion(@PathVariable long id, Model model) {
        model.addAttribute("question", qnaRepository.findById(id).orElseThrow(NoQuestionException::new));
        return "qna/show";
    }

    @GetMapping("{id}/form")
    public String editQna(@PathVariable long id, Model model, HttpSession session) {
        Question qna = qnaRepository.findById(id).orElseThrow(NoUserException::new);
        User sessionUser = HttpSessionUtils.getUserFromSession(session);

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        assert sessionUser != null;
        if (!qna.checkWriter(sessionUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }

        model.addAttribute("question", qna);

        return "qna/updateForm";
    }

    @PostMapping("{id}/form")
    public String update(@PathVariable long id, Question updateQna, HttpSession session) {
        Question qna = qnaRepository.findById(id).orElseThrow(NoUserException::new);
        qna.update(updateQna);
        qnaRepository.save(qna);
        return "redirect:/";
    }

    @DeleteMapping("{id}/delete")
    public String delete(@PathVariable long id, HttpSession session) {
        Question qna = qnaRepository.findById(id).orElseThrow(NoUserException::new);
        User sessionUser = HttpSessionUtils.getUserFromSession(session);

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        assert sessionUser != null;
        if (!qna.checkWriter(sessionUser)) {
            throw new IllegalStateException("자신의 질문만 삭제할 수 있습니다.");
        }

        qnaRepository.delete(qna);
        return "redirect:/";
    }

}
