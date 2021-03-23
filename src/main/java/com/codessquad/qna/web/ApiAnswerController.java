package com.codessquad.qna.web;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.web.HttpSessionUtils.getUserFromSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    public ApiAnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        Question question = questionService.findQuestion(questionId);
        Answer answer = new Answer(loggedinUser, question, contents);
        return answerService.save(answer);
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        Answer answer = answerService.findAnswer(answerId);
        answerService.delete(loggedinUser, answer);
        return "redirect:/questions/{questionId}";
    }
}
