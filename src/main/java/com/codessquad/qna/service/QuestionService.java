package com.codessquad.qna.service;

import com.codessquad.qna.domain.DisplayStatus;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.dto.UserDto;
import com.codessquad.qna.exception.NoSearchObjectException;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.valid.QuestionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public void write(Question question, UserDto user) {
        question.changeWriter(user.returnEntity());
        QuestionValidator.validate(question);

        questionRepository.save(question);
    }

    public Question findById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new NoSearchObjectException("질문"));
    }

    @Transactional
    public void update(Long questionId, Question question, UserDto user) {
        Question findQuestion = findById(questionId);
        findQuestion.checkSameUser(user.getId());
        findQuestion.questionUpdate(question);
        QuestionValidator.validate(findQuestion);
    }

    @Transactional
    public void delete(Long id, UserDto user) {
        Question question = findById(id);
        question.checkSameUser(user.getId());
        question.checkSameUserFromOpenAnswer();
        question.changeStatus(DisplayStatus.CLOSE);
    }

}
