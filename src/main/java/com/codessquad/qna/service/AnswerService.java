package com.codessquad.qna.service;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.answer.AnswerRepository;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.exception.AnswerNotFoundException;
import com.codessquad.qna.exception.QuestionNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public Answer create(Long questionId, Answer answer, User user) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));
        answer.setWriter(user);
        answer.setQuestion(question);
        question.addAnswer(answer);
        return answerRepository.save(answer);
    }

    @Transactional
    public Answer findById(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException(id));
    }

    @Transactional
    public List<Answer> findAllByQuestionId(Long questionId) {
        return answerRepository.findAllByQuestionIdAndDeletedIsFalse(questionId);
    }

    @Transactional
    public Long update(Long id, Answer answerWithUpdateInfo) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException(id));
        answer.update(answerWithUpdateInfo);
        return id;
    }

    @Transactional
    public Long deleteById(Long id, User user) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException(id));
        answer.isWrittenBy(user);
        Question question = answer.getQuestion();
        answer.delete();
        question.downCountOfAnswer();
        return id;
    }
}
