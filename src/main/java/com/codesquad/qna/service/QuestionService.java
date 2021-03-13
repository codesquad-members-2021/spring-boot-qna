package com.codesquad.qna.service;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    public void save(Question question) {
        questionRepository.save(question);
    }

    public Question findQuestionById(long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> (new ResponseStatusException(HttpStatus.NOT_FOUND, "Question Not Found.")));
    }
}
