package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public String createQuestion(Question newQuestion, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        newQuestion.setWriter(sessionUser);
        if (!isValidQuestion(newQuestion)) {
            return "question/form";
        }

        questionService.add(newQuestion);

        return "redirect:/";
    }

    @GetMapping("/form")
    public String moveToQuestionForm(Question newQuestion, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "user/login";
        }
        return "question/form";
    }

    private boolean isValidQuestion(Question question) {
        if (question == null) {
            return false;
        }
        if (question.getWriter() == null) {
            return false;
        }
        if ("".equals(question.getTitle()) || question.getTitle() == null) {
            return false;
        }
        if ("".equals(question.getContents()) || question.getContents() == null) {
            return false;
        }

        return true;
    }

    @GetMapping("/{id}")
    public String showQuestionInDetail(@PathVariable long id, Model model) {
        model.addAttribute("question", questionService.getOneById(id).orElse(null));

        return "question/show";
    }

    @GetMapping("/{id}/form")
    public String moveToUpdateForm(@PathVariable long id, Model model, HttpSession session) {

        // TODO: writer의 userId와 sessionedUser의 userId 일치할 경우만 questoin/update로 이동
        // TODO: writer의 userId를 가져오는 방법?

        return "question/update";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable long id, Question referenceQuestion) {
        Question presentQuestion = questionService.getOneById(id).orElse(null);
        referenceQuestion.setWriter(presentQuestion.getWriter());
        questionService.updateInfo(presentQuestion, referenceQuestion);

        return "redirect:/";
    }
}
