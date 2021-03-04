package com.codessquad.qna.repository;

import com.codessquad.qna.model.Question;

import java.util.ArrayList;
import java.util.List;

public class Questions {

    private final List<Question> questionList = new ArrayList<>();

    public void addQuestion(Question question) {
        Question newQuestion = new Question();
        newQuestion.setId(this.questionList.size() + 1);
        newQuestion.setWriter(question.getWriter());
        newQuestion.setTitle(question.getTitle());
        newQuestion.setContents(question.getContents());
        newQuestion.setDate();
        questionList.add(newQuestion);
    }

    public List<Question> getAllQuestion() {
        return this.questionList;
    }

    public Question findQuestion(int id) {
        return this.questionList.stream()
                .filter(question -> question.getId() == id)
                .findFirst()
                .get();
    }

}
