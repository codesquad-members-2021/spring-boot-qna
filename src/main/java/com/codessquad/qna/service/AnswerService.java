package com.codessquad.qna.service;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.answer.AnswerRepository;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.exception.AnswerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    /**
     * 답변 생성
     */
    public void createAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    /**
     * 답변 조회
     */
    public Answer findBy(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(AnswerNotFoundException::new);
    }

    /**
     * 답변 리스트 조회
     */
    public List<Answer> findAnswerListBy(Question question) {
        return answerRepository.findByQuestion(question);
    }

    /**
     * 답변 삭제
     */
    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }
}
