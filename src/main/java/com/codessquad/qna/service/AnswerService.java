package com.codessquad.qna.service;

import com.codessquad.qna.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public long create(User user, long questionId, String contents) {
        Question question = questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new);
        Answer answer = new Answer(user, question, contents);
        answerRepository.save(answer);
        return question.getId();
    }

    public List<Answer> findAnswersByQuestionId(long questionId) {
        return answerRepository.findAnswersByQuestionId(questionId);
    }

    @Transactional
    public long delete(long answerId, User user) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(IllegalArgumentException::new);
        if (verifyAnswer(answer, user)) {
            answerRepository.delete(answer);
        }
        return answer.getQuestion().getId();
    }

    public boolean verifyAnswer(Answer answer, User sessionedUser) {
        return sessionedUser.isMatchingUserId(answer.getWriter());
    }
}
