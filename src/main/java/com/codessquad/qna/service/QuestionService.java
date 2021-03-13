package com.codessquad.qna.service;

import com.codessquad.qna.model.Question;
import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public boolean save(Question question, User sessionUser) {
        if (sessionUser.nonNull()) {
            question.save(sessionUser);
            this.questionRepository.save(question);
            return true;
        }
        return false;
    }

    public boolean update(Long id, Question question, User sessionUser) {
        Question targetQuestion = verifyQuestion(id, sessionUser);
        if (targetQuestion.nonNull()) {
            targetQuestion.update(question);
            this.questionRepository.save(targetQuestion);
            return true;
        }
        return false;
    }

    public boolean delete(Long id, User sessionUser) {
        Question targetQuestion = verifyQuestion(id, sessionUser);
        if (targetQuestion.nonNull()) {
            this.questionRepository.delete(targetQuestion);
            return true;
        }
        return false;
    }

    public Question verifyQuestion(Long id, User sessionUser) {
        Question question = findById(id);
        if (sessionUser.nonNull() && question.nonNull() && question.matchWriter(sessionUser)) {
            return question;
        }
        return new Question();
    }

    public List<Question> findAll() {
        List<Question> questionList = new ArrayList<>();
        this.questionRepository.findAll().forEach(questionList::add);
        return questionList;
    }

    public Question findById(Long id) {
        Optional<Question> question = this.questionRepository.findById(id);
        return question.orElseGet(Question::new);
    }

}
