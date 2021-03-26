package com.codessquad.qna.service;

import com.codessquad.qna.exception.EntityNotFoundException;
import com.codessquad.qna.exception.InvalidSessionException;
import com.codessquad.qna.model.Question;
import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.codessquad.qna.utils.HttpSessionUtils.getUserFromSession;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public void save(Question question, HttpSession session) {
        question.save(getUserFromSession(session));
        questionRepository.save(question);
    }

    public void update(Question oldQuestion, Question newQuestion, HttpSession session) {
        if (!getUserFromSession(session).getUserId().equals(newQuestion.getWriter())) {
            throw new InvalidSessionException();
        }
        oldQuestion.update(newQuestion);
        questionRepository.save(oldQuestion);
    }

    public void delete(Question question) {
        if (!questionRepository.findById(question.getId()).isPresent()) {
            throw new EntityNotFoundException(ErrorMessage.QUESTION_NOT_FOUND);
        }
        questionRepository.delete(question);
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.QUESTION_NOT_FOUND));
    }

    public List<Question> findAll() {
        List<Question> questionList = new ArrayList<>();
        questionRepository.findAll().forEach(questionList::add);
        return questionList;
    }

    public void verifyWriter(User sessionedUser, Question question) {
        if (!question.matchWriter(sessionedUser)) {
            throw new InvalidSessionException();
        }
    }
}
