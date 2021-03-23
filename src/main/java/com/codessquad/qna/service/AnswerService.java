package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.type.NotFoundException;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.utils.HttpSessionUtils;
import com.codessquad.qna.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by 68936@naver.com on 2021-03-20 오후 2:36
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
@Service
public class AnswerService {
    private AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void save(HttpSession session, Answer answer) {
        HttpSessionUtils.checkValidOf(session);
        answer = Optional.ofNullable(answer).orElseThrow(IllegalArgumentException::new);

        User findUser = HttpSessionUtils.getLoginUserOf(session);
        answer.setReplyId(findUser.getUserId());
        answer.setReplyAuthor(findUser.getName());
        answerRepository.save(answer);
    }

    public Answer findById(Long id) {
        return answerRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void delete(HttpSession session, Long answerId) {
        ValidUtils.checkIllegalArgumentOf(answerId);
        HttpSessionUtils.checkValidOf(session);

        User loginUser = HttpSessionUtils.getLoginUserOf(session);
        Answer selectedAnswer = findById(answerId);

        ValidUtils.authenticateOfId(loginUser.getUserId(), selectedAnswer.getReplyId());
        if (!selectedAnswer.isDeleted()) {   // soft delete
            selectedAnswer.setAnswerDeleted(true);
        }
        save(session, selectedAnswer); // soft delete
    }

    public Answer getSelectedAnswers(Question questionData, Long answerId) {
        questionData = Optional.ofNullable(questionData).orElseThrow(IllegalArgumentException::new);
        ValidUtils.checkIllegalArgumentOf(answerId);

        for (Answer answer : questionData.getAnswers()) {
            if (Objects.equals(answer.getAnswerId(), answerId)) {
                return answer;
            }
        }
        throw new NotFoundException();
    }

    public void update(HttpSession session, Long id, String replyContents) {
        User loginUser = HttpSessionUtils.getLoginUserOf(session);
        Answer selectedAnswer = findById(id);
        ValidUtils.authenticateOfId(loginUser.getUserId(), selectedAnswer.getReplyId());
        selectedAnswer.setReplyContents(replyContents);
        save(session, selectedAnswer);
    }
}
