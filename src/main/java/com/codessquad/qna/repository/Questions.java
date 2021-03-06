package com.codessquad.qna.repository;

import com.codessquad.qna.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Questions {

    private int questionId = 0;
    private final List<Question> questionList = new ArrayList<>();

    public void addQuestion(Question question) {
        this.questionId++;
        question.setId(this.questionId);
        question.setDate();
        questionList.add(question);
    }

    public List<Question> getAllQuestion() {
        return this.questionList;
    }

    public Question findQuestion(int id) {
        int index = getQuestionIndex(id);
        if (index != -1) {
            return this.questionList.get(index);
        }
        return new Question();
    }

    private int getQuestionIndex(int id) {
        return IntStream.range(0, this.questionList.size())
                .filter(i -> this.questionList.get(i).getId() == id)
                .findFirst()
                .orElse(-1);
    }

}
