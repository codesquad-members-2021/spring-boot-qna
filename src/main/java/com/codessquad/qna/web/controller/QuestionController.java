package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.answer.Answer;
import com.codessquad.qna.web.domain.answer.AnswerRepository;
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
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionController(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping()
    public String create(QuestionRequest request, HttpSession session) {
        User loginUser = SessionUtils.getLoginUser(session);
        Question question = Question.toEntity(loginUser, request);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        if (!SessionUtils.isLoginUser(session)) {
            return "/users/login-form";
        }
        return "qna/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        Question question = getQuestionById(id);
        User writer = question.getWriter();
        User user = SessionUtils.getLoginUser(session);

        if (!writer.isMatchingId(user.getId())) {
            throw new CRUDAuthenticationException("Cannot edit other user's posts");
        }
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable long id, QuestionRequest post) {
        Question question = getQuestionById(id);
        question.update(post.getTitle(), post.getContents());
        questionRepository.save(question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable long id, HttpSession session) {
        Question question = getQuestionById(id);
        User user = SessionUtils.getLoginUser(session);
        if (!question.isMatchingWriter(user)) {
            throw new CRUDAuthenticationException("Cannot edit other user's posts");
        }
        questionRepository.delete(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        Question question = getQuestionById(id);
        List<Answer> answers = answerRepository.findByQuestionId(id);
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        return "/qna/show";
    }

    private Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Cannot found question number " + id));
    }

}
