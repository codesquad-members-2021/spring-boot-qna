package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
<<<<<<< HEAD
import com.codessquad.qna.repository.QuestionRepository;
=======
import com.codessquad.qna.domain.QuestionRepository;
>>>>>>> 34c9f84725566db738f456b1ef6a70331028c6b6
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.AccessDeniedException;
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
<<<<<<< HEAD
    public String createNewQna(Question qna, HttpSession session) {
=======
    public String createNewQuestion(Question question, HttpSession session) {
>>>>>>> 34c9f84725566db738f456b1ef6a70331028c6b6
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
<<<<<<< HEAD
        assert sessionUser != null;
        qna.setWriter(sessionUser);
=======
        question.setWriter(sessionUser);
>>>>>>> 34c9f84725566db738f456b1ef6a70331028c6b6

        qnaRepository.save(question);
        return "redirect:/";
    }

    @GetMapping
<<<<<<< HEAD
    public String qnaList(Model model) {
=======
    public String showQuestionList(Model model) {
>>>>>>> 34c9f84725566db738f456b1ef6a70331028c6b6
        model.addAttribute("question", qnaRepository.findAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String showOneQuestion(@PathVariable long id, Model model) {
        model.addAttribute("question", qnaRepository.findById(id).orElseThrow(NoQuestionException::new));
        return "qna/show";
    }

    @GetMapping("{id}/form")
<<<<<<< HEAD
    public String editQna(@PathVariable long id, Model model, HttpSession session) {
        Question qna = qnaRepository.findById(id).orElseThrow(NoUserException::new);
=======
    public String editQuestion(@PathVariable long id, Model model, HttpSession session) {
        Question question = qnaRepository.findById(id).orElseThrow(NoUserException::new);
>>>>>>> 34c9f84725566db738f456b1ef6a70331028c6b6
        User sessionUser = HttpSessionUtils.getUserFromSession(session);

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
<<<<<<< HEAD
        assert sessionUser != null;
        if (!qna.checkWriter(sessionUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }

        model.addAttribute("question", qna);
=======
        if (question.userConfirmation(sessionUser)) {
            throw new AccessDeniedException();
        }

        model.addAttribute("question", question);
>>>>>>> 34c9f84725566db738f456b1ef6a70331028c6b6

        return "qna/updateForm";
    }

<<<<<<< HEAD
    @PostMapping("{id}/form")
    public String update(@PathVariable long id, Question updateQna, HttpSession session) {
        Question qna = qnaRepository.findById(id).orElseThrow(NoUserException::new);
        qna.update(updateQna);
        qnaRepository.save(qna);
=======
    @PostMapping("{id}")
    public String updateQuestion(@PathVariable long id, Question updateQuestion) {
        Question question = qnaRepository.findById(id).orElseThrow(NoUserException::new);
        question.update(updateQuestion);
        qnaRepository.save(question);
>>>>>>> 34c9f84725566db738f456b1ef6a70331028c6b6
        return "redirect:/";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable long id, HttpSession session) {
<<<<<<< HEAD
        Question qna = qnaRepository.findById(id).orElseThrow(NoUserException::new);
=======
        Question question = qnaRepository.findById(id).orElseThrow(NoUserException::new);
>>>>>>> 34c9f84725566db738f456b1ef6a70331028c6b6
        User sessionUser = HttpSessionUtils.getUserFromSession(session);

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
<<<<<<< HEAD
        assert sessionUser != null;
        if (!qna.checkWriter(sessionUser)) {
            throw new IllegalStateException("자신의 질문만 삭제할 수 있습니다.");
=======
        if (question.userConfirmation(sessionUser)) {
            throw new AccessDeniedException();
>>>>>>> 34c9f84725566db738f456b1ef6a70331028c6b6
        }

        qnaRepository.delete(question);
        return "redirect:/";
    }

}
