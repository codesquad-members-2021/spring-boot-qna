package com.codessquad.qna.service;

import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Long create(Question question) {
        return questionRepository.save(question).getId();
    }

    @Transactional
    public Long update(Long id, Question questionWithUpdatedInfo) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 없습니다. id = " + id));
        question.update(questionWithUpdatedInfo);

        return id;
    }

    @Transactional
    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 없습니다. id = " + id));
    }

    @Transactional
    public List<Question> findAll() {
        return questionRepository.findAllByDeletedIsFalse();
    }

    @Transactional
    public void deleteById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 없습니다. id = " + id));
        question.setDeleted(true);
    }
}
