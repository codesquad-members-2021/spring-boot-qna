package com.codessquad.qna.controller;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.NotLoggedInException;
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
        Question question = questionRepostory.findById(questionId).orElseThrow(() -> new NotFoundException(" 메시지 유틸 수정해야 함"));
        //@Todo : 해당 메시지 유틸 수정 필요
        //@Todo : 메시지 유틸 수정 필요하닥 * 2
        Answer answer = new Answer(loginUser, question, contents);
        return answerRepository.save(answer);

    }

    @DeleteMapping
    public void remove(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        //델리트 요청이 들어오면 ->> 댓글을 삭제
        if (!isLoginUser(session)) {
            throw new NotLoggedInException();
        }
        //댓글 삭제
        User loginUser = getLoginUser(session);
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new NotFoundException(" 메시지 유틸 수정해야 함"));
        return;
    }

}
