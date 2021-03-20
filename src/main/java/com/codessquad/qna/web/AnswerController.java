package com.codessquad.qna.web;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.AnswerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.web.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        Question question = answerService.findQuestion(questionId);
        Answer answer = new Answer(loggedinUser, question, contents);
        answerService.save(answer);
        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable Long answerId, Long questionId, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        Answer answer = answerService.findAnswer(answerId);
        answerService.delete(loggedinUser, answer);
        return "redirect:/questions/{questionId}";
    }
}
