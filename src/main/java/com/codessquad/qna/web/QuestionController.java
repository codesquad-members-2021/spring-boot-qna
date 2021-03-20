package com.codessquad.qna.web;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.web.HttpSessionUtils.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questionService.getQuestionList());
        return "index";
    }

    @PostMapping("/create")
    public String create(String title, String contents, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        Question question = new Question(loggedinUser, title, contents);
        questionService.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.findQuestion(id));
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String update(@PathVariable Long id, Model model, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        Question question = questionService.findQuestion(id);
        questionService.checkValid(question, loggedinUser);
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateForm(@PathVariable Long id, Question updatedQuestion, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        Question question = questionService.findQuestion(id);
        questionService.update(question, updatedQuestion, loggedinUser);
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        Question question = questionService.findQuestion(id);
        questionService.delete(question, loggedinUser);
        return "redirect:/";
    }
}
