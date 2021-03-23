package com.codessquad.qna.web;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NoQuestionException;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.service.AnswerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("")
    public String createAnswer(@PathVariable long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User user = HttpSessionUtils.getUserFromSession(session);
        Question question = answerService.getQuestionById(questionId);

        Answer answer = new Answer(user, question, contents);

        answerService.save(answer);
        return String.format("redirect:/question/%d", questionId);
    }
}
