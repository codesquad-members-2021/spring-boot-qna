package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.domain.repository.QuestionRepository;
import com.codessquad.qna.web.exception.QuestionNotFoundException;
import com.codessquad.qna.web.exception.UnauthorizedUserException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    private QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void save(Question question) {
        questionRepository.save(question);
    }

    public void update(Question question, String title, String contents) {
        question.update(title, contents);
        save(question);
    }

    public void delete(Question question) { questionRepository.delete(question); }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question findById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException());
        return question;
    }

    public void verifyQuestionWriter(Question question, User user) {
        if (!question.isWriter(user)) {
            throw new UnauthorizedUserException(UnauthorizedUserException.UNAUTHORIZED_USER_TO_QUESTION);
        }
    }
}
