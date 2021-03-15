package com.codessquad.qna.service;

import com.codessquad.qna.domain.DisplayStatus;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.valid.QuestionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public void write(Question question, User user) {
        question.changeWriter(user);
        question.setCreateDateTime(LocalDateTime.now());
        QuestionValidator.validate(question);

        questionRepository.save(question);
    }

    public Question findById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(NullPointerException::new);
    }

    @Transactional
    public void update(Long id, Question question) {
        Question findQuestion = findById(id);
        findQuestion.questionUpdate(question);
        QuestionValidator.validate(findQuestion);
    }

    @Transactional
    public void delete(Long id) {
        Question question = findById(id);
        if (question.checkSameUserFromAnswer()) {
            question.changeStatus(DisplayStatus.CLOSE);
        }
    }

}
