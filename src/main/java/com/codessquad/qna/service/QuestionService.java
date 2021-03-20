package com.codessquad.qna.service;

import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.UserSessionException;
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

    public boolean delete(Long id, User sessionUser) {
        Question targetQuestion = verifyQuestion(id, sessionUser);
        if (targetQuestion.matchWriterOfAnswerList()) {
            targetQuestion.delete();
            this.questionRepository.save(targetQuestion);
            return true;
        }
        return false;
    }

    public Question verifyQuestion(Long id, User sessionUser) {
        Question question = findById(id);
        if (!question.matchWriter(sessionUser)) {
            throw new UserSessionException();
        }
        return question;
    }

    public List<Question> findAll() {
        return this.questionRepository.findAllByDeletedFalse();
    }

    public Question findById(Long id) {
        return this.questionRepository.findByIdAndDeletedFalse(id).orElseThrow(NotFoundException::new);
    }

}
