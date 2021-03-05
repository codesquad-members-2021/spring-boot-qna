package com.codessquad.qna.web.question;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QuestionMapper {
    private Map<Integer, Question> questions = new LinkedHashMap<>();

    public void add(Question question) {
        int questionId = questions.size() + 1;
        question.setQuestionId(questionId);
        questions.put(questionId, question);
    }

    public List<Question> getQuestions() {
        return questions.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public Question getQuestion(int questionId) {
        // TODO: 존재하지 않는 questionId 로 요청했을 때에 대한 예외처리 필요
        return questions.getOrDefault(questionId, new Question());
    }
}
