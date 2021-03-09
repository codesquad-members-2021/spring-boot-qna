package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void create(Answer answer) {
        answerRepository.save(answer);
    }
}

