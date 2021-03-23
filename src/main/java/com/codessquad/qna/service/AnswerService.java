package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.AnswerRepository;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.dto.AnswerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void create(AnswerDto answerDto, Question question, User user){
        Answer answer = answerDto.toEntity(question, user);
        answerRepository.save(answer);
    }

    public List<Answer> findAnswersByQuestionId(long id){
        return answerRepository.findAnswersByQuestionId(id);
    }
}
