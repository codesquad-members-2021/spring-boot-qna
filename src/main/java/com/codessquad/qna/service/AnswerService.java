package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.type.NotFoundException;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    public void save(User findUser, Answer answer){
        answer.setReplyId(findUser.getUserId());
        answer.setReplyAuthor(findUser.getName());
        answerRepository.save(answer);
    }

    public Answer findById(Long id){
        return answerRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void delete(User loginUser, Answer selectedAnswer){
        ValidUtils.authenticateOfId(loginUser.getUserId(), selectedAnswer.getReplyId());
        if(!selectedAnswer.isDeleted()){   // soft delete
            selectedAnswer.setAnswerDeleted(true);
        }
        save(loginUser,selectedAnswer); // soft delete
    }

    public Answer getSelectedAnswers(Question questionData, Long answerId){
        for (Answer answer : questionData.getAnswers()) {
            if (Objects.equals(answer.getAnswerId(), answerId)) {
                return answer;
            }
        }
        throw new NotFoundException();
    }

    public void update(User loginUser, Answer selectedAnswer, String replyContents){
        ValidUtils.authenticateOfId(loginUser.getUserId(), selectedAnswer.getReplyId());
        selectedAnswer.setReplyContents(replyContents);
        save(loginUser, selectedAnswer);
    }
}
