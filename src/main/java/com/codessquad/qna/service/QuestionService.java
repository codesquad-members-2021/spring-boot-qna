package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepositoryimpl;

import java.util.List;

public class QuestionService {

    private QuestionRepositoryimpl questionRepository = new QuestionRepositoryimpl();

    public void registerQuestion(Question question) {

        questionRepository.save(question);
    }

    public List<Question> findQuestions(){
        return questionRepository.findQuestionList();
    }

    public Question findQuestion(int index){

        return questionRepository.findQuestionByIndex(index);
    }

}
