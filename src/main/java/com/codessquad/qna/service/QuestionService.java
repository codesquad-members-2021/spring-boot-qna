package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.valid.QuestionValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class QuestionService {

    private QuestionRepository questionRepository;
    private final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public void write(Question question, User user) {
        question.changeWriter(user);
        question.setWriteTime(LocalDateTime.now());
        QuestionValidation.validQuestion(question);

        questionRepository.save(question);
    }

    public Question findById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(NullPointerException::new);
    }

    @Transactional
    public void update(Long id, Question question) {
        Question findQuestion = findById(id);
        //todo : 업데이트 질문의 검증 필요 -> 기존의 검증은 writer까지 검증 -> 오류 발생
        findQuestion.questionUpdate(question);
    }

    @Transactional
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }
}
