package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions.*;

class QuestionRepositoryimplTest {

    QuestionRepositoryimpl repositoryimpl = new QuestionRepositoryimpl();
    private List<Question> questions = new ArrayList<>();

    Question q1;
    @Test
    void save(){
        q1 = new Question();
        q1.setTitle("q1");

        Question q2 = new Question();
        q1.setTitle("q2");

        repositoryimpl.save(q1);
        repositoryimpl.save(q2);
    }

    @Test
    public Question findQuestionByIndex(int index) {

        return questions.get(index);

    }

}