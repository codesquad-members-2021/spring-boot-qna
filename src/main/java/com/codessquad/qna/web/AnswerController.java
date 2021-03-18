package com.codessquad.qna.web;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.AnswerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.web.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerRepository answerRepository;

    public AnswerController(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @PostMapping
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        Answer answer = new Answer(loggedinUser, contents);
        answerRepository.save(answer);
        return "redicert:/questions/{questionId}";
    }
}
