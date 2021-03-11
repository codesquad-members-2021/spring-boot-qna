package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepositoryimpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class QuestionService {

    private QuestionRepositoryimpl questionRepository;

    public QuestionService(QuestionRepositoryimpl questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void registerQuestion(Question question) {
        questionRepository.save(question);
    }

    public List<Question> findQuestions(){

        return questionRepository.findQuestionList();
    }

    public Optional<Question> findQuestion(int index){

        return questionRepository.findQuestionByIndex(index);
    }

}
