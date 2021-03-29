package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.dto.QuestionDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public void create(QuestionDto questionDto, User user) {
        Question question = questionDto.toEntity(user);
        questionRepository.save(question);
    }

    @Transactional
    public void update(Question updateQuestion, long questionId, User sessionedUser) {
        Question question = verifyQuestion(questionId, sessionedUser);
        question.update(updateQuestion);
        questionRepository.save(question);
    }

    public List<Question> findQuestions() {
        return questionRepository.findAll();
    }

    public Question findQuestionById(long questionId) {
        return questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void delete(long questionId, User user) {
        Question question = verifyQuestion(questionId, user);
        questionRepository.delete(question);
    }

    public Question verifyQuestion(long questionId, User sessionedUser) {
        Question question = findQuestionById(questionId);
        if (!sessionedUser.isMatchingUserId(question.getWriter())) {
            throw new IllegalStateException("자신의 질문만 수정할 수 있습니다.");
        }
        return question;
    }
}
