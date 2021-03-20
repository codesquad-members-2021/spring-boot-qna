package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
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
    public String create(String title, String contents, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        Question question = new Question(loggedinUser, title, contents);
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
        User loggedinUser = getUserFromSession(session);
        Question question = getQuestionById(id);
        checkValid(question, loggedinUser);
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateForm(@PathVariable Long id, String title, String contents, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        Question question = getQuestionById(id);
        checkValid(question, loggedinUser);
        question.update(title, contents);
        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        Question question = getQuestionById(id);
        checkValid(question, loggedinUser);
        questionRepository.delete(question);
        return "redirect:/";
    }

    private Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));
    }

    private boolean checkValid(Question question, User user) {
        if (!question.isPostWriter(user)) {
            throw new IllegalStateException("자신의 질문만 접근 가능합니다.");
        }
        return true;
    }
}
