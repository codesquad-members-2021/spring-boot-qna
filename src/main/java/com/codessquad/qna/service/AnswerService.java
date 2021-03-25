package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.AnswerRepository;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.dto.AnswerDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void create(AnswerDto answerDto, Question question, User user) {
        Answer answer = answerDto.toEntity(question, user);
        answerRepository.save(answer);
    }


    public List<Answer> findAnswersByQuestionId(long questionId) {
        return answerRepository.findAnswersByQuestionId(questionId);
    }

    @Transactional
    public void delete(long answerId, User user) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(IllegalArgumentException::new);
        if (verifyAnswer(answer, user)) {
            answerRepository.delete(answer);
        }
    }

    public boolean verifyAnswer(Answer answer, User sessionedUser) {
        return sessionedUser.isMatchingUserId(answer.getWriter());
    }
}
