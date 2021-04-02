package com.codessquad.qna.service;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.exception.CrudNotAllowedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    @Transactional
    public void create(User user, long questionId, String contents) {
        Question question = questionService.findQuestionById(questionId);
        Answer answer = new Answer(user, question, contents);
        answerRepository.save(answer);
    }

    public List<Answer> findAnswersByQuestionId(long questionId) {
        return answerRepository.findAnswersByQuestionId(questionId);
    }

    public Answer findAnswerByAnswerId(long answerId) {
        return answerRepository.findById(answerId).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void delete(Answer answer, User user) {
        if (answer.isMatchingWriter(user)) {
            answerRepository.delete(answer);
        } else {
            throw new CrudNotAllowedException("자신의 답변만 삭제할 수 있습니다.");
        }
    }
}
