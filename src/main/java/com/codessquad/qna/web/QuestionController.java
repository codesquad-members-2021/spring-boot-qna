package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NoQuestionException;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.web.HttpSessionUtils.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @PostMapping("/create")
    public String create(Question question, HttpSession session) {
        isLoginUser(session);
        question.setWriter(getUserFromSession(session));
        question.setPostTime();
        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", getQuestionById(id));
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String update(@PathVariable Long id, Model model, HttpSession session) {
        isLoginUser(session);
        Question question = getQuestionById(id);
        User sessionedUser = getUserFromSession(session);
        if (!question.isPostWriter(sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateForm(@PathVariable Long id, Question updatedQuestion, HttpSession session) {
        isLoginUser(session);
        Question question = getQuestionById(id);
        User sessionedUser = getUserFromSession(session);
        if (!question.isPostWriter(sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        question.update(updatedQuestion);
        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        isLoginUser(session);
        Question question = getQuestionById(id);
        User sessionedUser = getUserFromSession(session);
        if (!question.isPostWriter(sessionedUser)) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        questionRepository.delete(question);
        return "redirect:/";
    }

    private Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(NoQuestionException::new);
    }
}
