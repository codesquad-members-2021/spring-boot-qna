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
        //todo : 관계메소드로 변경
        question.setWriter(user);
        question.setWriteTime(LocalDateTime.now());
        QuestionValidation.validQuestion(question);

        questionRepository.save(question);
    }

    public Question findById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(NullPointerException::new);
    }

}
