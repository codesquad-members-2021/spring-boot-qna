package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.validationGroup.Submit;
import com.codessquad.qna.exception.NotAuthorizedException;
import com.codessquad.qna.exception.UserNotFoundInSessionException;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.util.HttpSessionUtils;
import com.codessquad.qna.util.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String list(int page, Model model) {
        Page<Question> questionPage = questionService.getQuestions(page);
        int totalPageNum = questionPage.getTotalPages();

        List<Integer> pageNumbers = PageUtils.getPageNumbers(page, totalPageNum, 5);
        int prevPageNum = PageUtils.getPrevPageNumber(pageNumbers);
        int nextPageNum = PageUtils.getNextPageNumber(pageNumbers, totalPageNum);

        model.addAttribute("questions", questionPage.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("prevPageNum", prevPageNum);
        model.addAttribute("nextPageNum", nextPageNum);
        return "qna/list";
    }

    @PostMapping
    public String create(@Validated(Submit.class) Question question, HttpSession session) {
        User user = HttpSessionUtils.getUser(session);
        questionService.addQuestion(user, question.getTitle(), question.getContents());
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, @Validated(Submit.class) Question question, HttpSession session) {
        User tryToUpdate = HttpSessionUtils.getUser(session);
        questionService.updateQuestion(id, question.getTitle(), question.getContents(), tryToUpdate);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, HttpSession session) {
        User tryToDelete = HttpSessionUtils.getUser(session);
        questionService.deleteQuestion(id, tryToDelete);
        return "redirect:/";
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.hasUser(session)) {
            throw new UserNotFoundInSessionException();
        }
        return "qna/form";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable int id, Model model, HttpSession session) {
        Question question = questionService.getQuestion(id);
        if (!HttpSessionUtils.isAuthorized(question.getWriter().getId(), session)) {
            throw new NotAuthorizedException();
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }
}
