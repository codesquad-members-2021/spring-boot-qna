package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final Logger logger = LoggerFactory.getLogger(QuestionService.class);
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void add(Question newQuestion){
        Question question = questionRepository.save(newQuestion);
        logger.info("after save" + question.toString());
    }

    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

    public Optional<Question> getOneById(Long id){
        return questionRepository.findById(id);
    }

    public void updateInfo(Question presentQuestion, Question referenceQuestion) {
        // TODO: setter 안쓰고 id 바꾸는 방법?
        referenceQuestion.setId(presentQuestion.getId());

        questionRepository.delete(presentQuestion);
        questionRepository.save(referenceQuestion);
    }

    public void remove(long id) {
        questionRepository.deleteById(id);
    }
}
