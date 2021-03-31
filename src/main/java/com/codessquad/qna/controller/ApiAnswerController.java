package com.codessquad.qna.controller;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.NotLoggedInException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if(!isLoginUser(session)) {
            throw new NotLoggedInException();
        }

        User loginUser = getLoginUser(session);
        Question question = questionRepostory.findById(questionId).orElseThrow(() -> new NotFoundException(" 메시지 유틸 수정해야 함"));
        //@Todo : 메시지 유틸
        Answer answer = new Answer(loginUser,question,contents);
        return answerRepository.save(answer);

    }

}
