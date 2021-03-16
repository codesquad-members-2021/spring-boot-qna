package com.codesquad.qna.service;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.repository.QuestionRepository;
import com.codesquad.qna.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public void save(Question question, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionedUser.getUserId(), question.getTitle(), question.getContents());
        questionRepository.save(newQuestion);
    }

    public Question findQuestionById(long id) {
        return questionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }
}
