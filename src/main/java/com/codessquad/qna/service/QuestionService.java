package com.codessquad.qna.service;

import com.codessquad.qna.model.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void save(Question question) {
        question.setDate();
        this.questionRepository.save(question);
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
