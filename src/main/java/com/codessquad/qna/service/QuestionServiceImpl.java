package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.ForbiddenException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void deleteQuestion(Long id, User loginUser) {
        Question question = getQuestionWithAuthentication(id, loginUser);
        List<Answer> answers = question.getAnswers();
        for (Answer answer : answers) {
            if (!answer.matchesWriter(loginUser)) {
                throw new ForbiddenException("다른 사람의 답변이 존재하여 질문을 삭제할 수 없습니다.");
            }
        }
        for (Answer answer : answers) {
            answer.delete();
        }
        question.delete();
        questionRepository.save(question);
    }
}
