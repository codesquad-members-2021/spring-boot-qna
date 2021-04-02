package com.codessquad.qna.controller;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.NotLoggedInException;
import com.codessquad.qna.exception.UnauthorizedAnswerException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.SessionUtil.getLoginUser;
import static com.codessquad.qna.utils.SessionUtil.isLoginUser;

@RestController
@RequestMapping("api/questions/{questionId}/answers")
public class ApiAnswerController {

    private final QuestionRepostory questionRepostory;
    private final AnswerRepository answerRepository;

    public ApiAnswerController(QuestionRepostory questionRepostory, AnswerRepository answerRepository) {
        this.questionRepostory = questionRepostory;
        this.answerRepository = answerRepository;
    }

    @PostMapping
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!isLoginUser(session)) {
            throw new NotLoggedInException();
        }

        User loginUser = getLoginUser(session);
        Question question = questionRepostory.findById(questionId).orElseThrow(() -> new NotFoundException());
        Answer answer = new Answer(loginUser, question, contents);
        return answerRepository.save(answer);

    }

    @DeleteMapping("/{answerId}")
    public void remove(@PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId, HttpSession session) {
        if (!isLoginUser(session)) {
            throw new NotLoggedInException();
        }
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new NotFoundException());
        if (!answer.getWriter().isSessionSameAsUser(session)) {
            throw new UnauthorizedAnswerException();
        }
        answerRepository.delete(answer);
        return;
    }

}
