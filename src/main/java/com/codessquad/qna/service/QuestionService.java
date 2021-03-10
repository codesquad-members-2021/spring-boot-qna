package com.codessquad.qna.service;

import com.codessquad.qna.model.Question;
import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codessquad.qna.controller.HttpSessionUtils.getUserFromSession;

@Service
public class QuestionService {

    @Autowired
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public boolean save(Question question, HttpSession session) {
        User loginUser = getUserFromSession(session);
        if (loginUser.nonNull()) {
            question.setWriter(loginUser.getUserId());
            question.setDate();
            this.questionRepository.save(question);
            return true;
        }
        return false;
    }

    public Question verifyQuestion(Long id, String writer, HttpSession session) {
        User loginUser = getUserFromSession(session);
        if (loginUser.nonNull() && loginUser.matchUserId(writer)) {
            return findById(id);
        }
        return new Question();
    }

    public boolean update(Long id, String writer, Question question, HttpSession session) {
        User loginUser = getUserFromSession(session);
        Question currentQuestion = findById(id);
        if (loginUser.nonNull() && loginUser.matchUserId(writer) && currentQuestion.nonNull()) {
            currentQuestion.update(question);
            this.questionRepository.save(currentQuestion);
            return true;
        }
        return false;
    }

    public boolean delete(Long id, String writer, HttpSession session) {
        User loginUser = getUserFromSession(session);
        Question question = findById(id);
        if (loginUser.nonNull() && loginUser.matchUserId(writer) && question.nonNull()) {
            this.questionRepository.delete(question);
            return true;
        }
        return false;
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
