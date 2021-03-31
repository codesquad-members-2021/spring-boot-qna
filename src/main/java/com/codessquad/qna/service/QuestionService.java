package com.codessquad.qna.service;

import com.codessquad.qna.exception.EntityNotFoundException;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.model.Question;
import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.exception.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void save(Question question, User sessionedUser) {
        question.save(sessionedUser);
        questionRepository.save(question);
    }

    public void update(Long questionId, Question newQuestion, User sessionedUser) {
        Question oldQuestion = verifyQuestion(questionId, sessionedUser);
        oldQuestion.update(newQuestion);
        questionRepository.save(oldQuestion);
    }

    public boolean delete(Long questionId, User sessionedUser) {
        Question question = verifyQuestion(questionId, sessionedUser);
        boolean result = question.delete();
        if (result) {
            questionRepository.save(question);
        }
        return result;
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessage.QUESTION_NOT_FOUND));
    }

    public List<Question> findAll() {
        return questionRepository.findAllByDeletedFalse();
    }

    public Question verifyQuestion(Long id, User sessionedUser) {
        Question question = findById(id);
        if (!question.matchWriter(sessionedUser)) {
            throw new IllegalUserAccessException();
        }
        return question;
    }
}
