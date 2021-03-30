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

import static com.codessquad.qna.exception.ExceptionMessages.*;
import static com.codessquad.qna.utils.SessionUtil.getLoginUser;
import static com.codessquad.qna.utils.SessionUtil.isLoginUser;

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
            throw new LoginFailedException();
        }
        User loginUser = getLoginUser(session);
        Answer answer = new Answer(loginUser, questionRepostory.getOne(questionId), contents);
        answerRepository.save(answer);
    }

    public void removeAnswer(Long answerId, HttpSession session) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new NotFoundException(NOT_FOUNDED_ANSWER));

        if (!answer.getWriter().isSessionSameAsUser(session)) {
            logger.debug(UNAUTHORIZED_FAILED_QUESTION);
            throw new UnauthorizedException(UNAUTHORIZED_FAILED_QUESTION);
        }

        answer.deleteAnswer();
        answerRepository.save(answer);
    }
}
