package com.codessquad.qna.service;

import com.codessquad.qna.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AnswerService{

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public void create(User user, long questionId, String contents) {
        Question question = questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new);
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
    public boolean delete(Answer answer, User user) {
        if (answer.isMatchingWriter(user)) {
            answerRepository.delete(answer);
            return true;
        }
        return false;
    }
}
