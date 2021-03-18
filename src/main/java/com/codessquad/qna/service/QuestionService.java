package com.codessquad.qna.service;

import com.codessquad.qna.domain.DisplayStatus;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NoSearchObjectException;
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
        return questionRepository.findById(questionId).orElseThrow(() -> new NoSearchObjectException("질문"));
    }

    @Transactional
    public void update(Long questionId, Question question, User user) {
        Question findQuestion = findById(questionId);
        user.checkSameUser(findQuestion.getWriter().getId());
        findQuestion.questionUpdate(question);
        QuestionValidator.validate(findQuestion);
    }

    @Transactional
    public void delete(Long id, User user) {
        Question question = findById(id);
        user.checkSameUser(question.getWriter().getId());
        question.checkSameUserFromOpenAnswer();
        question.changeStatus(DisplayStatus.CLOSE);
    }

}
