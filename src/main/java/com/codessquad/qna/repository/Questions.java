package com.codessquad.qna.repository;

import com.codessquad.qna.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Questions {

    private final List<Question> questionList = new ArrayList<>();

    public void addQuestion(Question question) {
        question.setId(this.questionList.size() + 1);
        question.setDate();
        questionList.add(question);
    }

    public List<Question> getAllQuestion() {
        return this.questionList;
    }

    public Question findQuestion(int id) {
        int index = getQuestionIndex(id);
        return this.questionList.get(index);
    }

    public void deleteQuestion(int id) {
        int index = getQuestionIndex(id);
        this.questionList.remove(index);
    }

    private int getQuestionIndex(int id) {
        int index = IntStream.range(0, this.questionList.size())
                .filter(i -> this.questionList.get(i).getId() == id)
                .findFirst()
                .orElse(-1);
        if (index == -1) {
            throw new IllegalStateException("해당 질문이 없습니다.");
        }
        return index;
    }

}
