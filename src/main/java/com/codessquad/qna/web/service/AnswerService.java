package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.Answer;
import com.codessquad.qna.web.domain.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private AnswerRepository answerRepository;

    private AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> findByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public void save(Answer answer) {
        answerRepository.save(answer);
    }
}
