package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> getQuestionList() {
        return questionRepository.findAllByDeleted(false);
    }

    @Override
    public void registerQuestion(Question question, User loginUser) {
        question.setWriter(loginUser);
        questionRepository.save(question);
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findByIdAndDeleted(id, false).orElseThrow(NotFoundException::new);
    }

    @Override
    public Question getQuestionWithAuthentication(Long id, User loginUser) {
        return questionRepository.findById(id)
                .filter(q -> q.isWriter(loginUser))
                .orElseThrow(() -> new UnauthorizedAccessException("다른 사람의 질문을 수정하거나 삭제할 수 없습니다."));
    }

    @Override
    public void updateQuestion(Long id, User loginUser, Question updatedQuestion) {
        Question question = getQuestionWithAuthentication(id, loginUser);
        question.updateContents(updatedQuestion);
        questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Long id, User loginUser) {
        Question question = getQuestionWithAuthentication(id, loginUser);
        question.delete();
        questionRepository.save(question);
    }
}
