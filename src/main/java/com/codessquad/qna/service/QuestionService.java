package com.codessquad.qna.service;

import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import com.codessquad.qna.exception.QuestionNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * 질문 생성
     */
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    /**
     * 질문 조회
     */
    public Question findBy(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(QuestionNotFoundException::new);
    }

    /**
     * 질문 리스트 조회
     */
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    /**
     * 질문 수정
     */
    @Transactional
    public void update(Question question, Question updateQuestion) {
        question.update(updateQuestion);
    }

    /**
     * 질문 제거
     */
    public void deleteBy(Long id) {
        questionRepository.deleteById(id);
    }
}
