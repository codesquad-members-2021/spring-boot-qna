package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.repository.SpringDataJpaQuestionRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class QnaService {

    private final QuestionRepository questionRepository;

    public QnaService(SpringDataJpaQuestionRepository springDataJpaQuestionRepository) {
        this.questionRepository = springDataJpaQuestionRepository;
    }

    public void save(Question question) {
        questionRepository.save(question);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question findQuestionById(Long id) {
        return questionRepository.findQuestionById(id);
    }
}
