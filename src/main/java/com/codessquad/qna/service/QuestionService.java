package com.codessquad.qna.service;

import com.codessquad.qna.controller.UserController;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void add(Question newQuestion){
        Question question = questionRepository.save(newQuestion);
        logger.info("after save" + question.toString());
    }

    public Iterable<Question> showAll(){
        return questionRepository.findAll();
    }

    public Question showOneById(Long id){
        return questionRepository.findById(id).get();
    }
}
