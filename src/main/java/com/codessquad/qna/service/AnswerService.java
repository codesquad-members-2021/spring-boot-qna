package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.AnswerRepository;
import com.codessquad.qna.domain.QuestionRepostory;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.LoginFailedException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.SessionUtil.*;

@Service
public class AnswerService {

    private static final Logger logger = LoggerFactory.getLogger(AnswerService.class);

    private final QuestionRepostory questionRepostory;
    private final AnswerRepository answerRepository;


    public AnswerService(QuestionRepostory questionRepostory, AnswerRepository answerRepository) {
        this.questionRepostory = questionRepostory;
        this.answerRepository = answerRepository;
    }


    public void createAnswer(Long questionId, String contents, HttpSession session) {
        if (!isLoginUser(session)) {
            logger.info("답변달기 - 실패 : 권한(로그인)되지 않은 사용자의 답변달기 시도가 실패함");
            throw new LoginFailedException();
        }
        User loginUser = getLoginUser(session);
        Answer answer = new Answer(loginUser, questionRepostory.getOne(questionId), contents);
        answerRepository.save(answer);
        logger.info("답변달기 - 성공 : 답변이 정상적으로 추가됨");
    }


    public void removeAnswer(Long questionId, Long answerId, HttpSession session) {
        User ownerUser = questionRepostory.findById(questionId).orElseThrow(NotFoundException::new).getWriter();
        if (!isValidUser(session, ownerUser)) {
            logger.info("답변달기 - 실패 : 권한(로그인)되지 않은 사용자의 답변달기 시도가 실패함");
            throw new UnauthorizedException("답변달기 - 실패 : 권한(로그인)되지 않은 사용자의 답변달기 시도가 실패함");
        }
        Answer answer = answerRepository.findById(answerId).orElseThrow(NotFoundException::new);
        answer.deleteAnswer();
        answerRepository.save(answer);
    }
}
