package com.codessquad.qna.web;

import com.codessquad.qna.domain.Qna;
import com.codessquad.qna.domain.QnaRepository;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NoQuestionException;
import com.codessquad.qna.exception.NoUserException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/qna")
public class QuestionController {
    private final QnaRepository qnaRepository;

    public QuestionController(QnaRepository qnaRepository) {
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
    public String createNewQna(Qna qna, HttpSession session) {
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
        model.addAttribute("qnaList", qnaRepository.findAll());
        return "index";
    }

    @GetMapping("/{qnaId}")
    public String showOneQuestion(@PathVariable long qnaId, Model model) {
        model.addAttribute("Qna", qnaRepository.findById(qnaId).orElseThrow(NoQuestionException::new));
        return "qna/show";
    }

    @GetMapping("{id}/form")
    public String editQna(@PathVariable long id, Model model, HttpSession session) {
        Qna qna = qnaRepository.findById(id).orElseThrow(NoUserException::new);
        User sessionUser = HttpSessionUtils.getUserFromSession(session);

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        assert sessionUser != null;
        if (!qna.checkWriter(sessionUser.getUserId())) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }

        model.addAttribute("qna", qna);

        return "qna/updateForm";
    }

    @PostMapping("{id}/form")
    public String update(@PathVariable long id, Qna updateQna, HttpSession session) {
        Qna qna = qnaRepository.findById(id).orElseThrow(NoUserException::new);
        qna.update(updateQna);
        qnaRepository.save(qna);
        return "redirect:/";
    }

    @DeleteMapping("{id}/delete")
    public String delete(@PathVariable long id, HttpSession session) {
        Qna qna = qnaRepository.findById(id).orElseThrow(NoUserException::new);
        User sessionUser = HttpSessionUtils.getUserFromSession(session);

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        assert sessionUser != null;
        if (!qna.checkWriter(sessionUser.getUserId())) {
            throw new IllegalStateException("자신의 질문만 삭제할 수 있습니다.");
        }

        qnaRepository.delete(qna);
        return "redirect:/";
    }

}
