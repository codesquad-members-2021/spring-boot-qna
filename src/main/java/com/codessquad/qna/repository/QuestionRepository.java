package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {
    private final List<Question> questionList = new ArrayList<>();

    public void save(Question newQuestion){
        questionList.add(newQuestion);
    }

    public List<Question> getAll(){
        return questionList;
    }
}
