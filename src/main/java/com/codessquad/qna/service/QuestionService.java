package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NoQuestionException;
import com.codessquad.qna.exception.NoUserException;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void save(User sessionUser, Question question) {
        question.setWriter(sessionUser);

        questionRepository.save(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public List<Question> getQuestionList() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(long id) {
        return questionRepository.findById(id).orElseThrow(NoQuestionException::new);
    }

    public void updateQuestion(long id, Question updateQuestion) {
        Question question = questionRepository.findById(id).orElseThrow(NoUserException::new);
        question.update(updateQuestion);
        questionRepository.save(question);
    }

}
