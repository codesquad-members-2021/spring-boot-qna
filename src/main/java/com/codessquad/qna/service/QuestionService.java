package com.codessquad.qna.service;

import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.exception.QuestionNotFoundException;
import com.codessquad.qna.exception.WriterOfAnswerListNotMatchException;
import com.codessquad.qna.model.Question;
import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void save(Question question, User sessionUser) {
        question.save(sessionUser);
        this.questionRepository.save(question);
    }

    public void update(Long id, Question question, User sessionUser) {
        Question targetQuestion = verifyQuestion(id, sessionUser);
        targetQuestion.update(question);
        this.questionRepository.save(targetQuestion);
    }

    public void delete(Long id, User sessionUser) {
        Question targetQuestion = verifyQuestion(id, sessionUser);
        if (!targetQuestion.matchWriterOfAnswerList()) {
            throw new WriterOfAnswerListNotMatchException(id);
        }
        targetQuestion.delete();
        this.questionRepository.save(targetQuestion);
    }

    public Question verifyQuestion(Long id, User sessionUser) {
        Question question = findById(id);
        if (!question.matchWriter(sessionUser)) {
            throw new IllegalUserAccessException();
        }
        return question;
    }

    public List<Question> findAll() {
        return this.questionRepository.findAllByDeletedFalse();
    }

    public Question findById(Long id) {
        Question question = this.questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        if (question.isDeleted()) {
            throw new QuestionNotFoundException();
        }
        return question;
    }

}
