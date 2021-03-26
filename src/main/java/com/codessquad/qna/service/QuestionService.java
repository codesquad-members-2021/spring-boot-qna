package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.dto.QuestionDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void create(QuestionDto questionDto, User user) {
        Question question = questionDto.toEntity(user);
        questionRepository.save(question);
    }


    public void save(Question updateQuestion) {
        questionRepository.save(updateQuestion);
    }

    public List<Question> findQuestions() {
        return questionRepository.findAll();
    }

    public Question findQuestionById(long questionId) {
        return questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public boolean verifyQuestion(Question question, User sessionedUser) {
        return sessionedUser.isMatchingUserId(question.getWriter());
    }
}
