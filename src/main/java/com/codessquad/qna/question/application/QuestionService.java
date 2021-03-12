package com.codessquad.qna.question.application;

import com.codessquad.qna.question.domain.Question;
import com.codessquad.qna.question.domain.QuestionRepository;
import com.codessquad.qna.question.dto.QuestionRequest;
import com.codessquad.qna.question.dto.QuestionResponse;
import com.codessquad.qna.question.exception.QuestionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public QuestionResponse saveQuestion(QuestionRequest questionRequest) {
        Question question = questionRepository.save(questionRequest.toQuestion());
        return QuestionResponse.of(question);
    }

    public List<QuestionResponse> getQuestions() {
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : questionRepository.findAll()) {
            questionResponses.add(QuestionResponse.of(question));
        }
        return questionResponses;
    }

    public QuestionResponse getQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("존재하지 않는 질문입니다. id: " + id));
        return QuestionResponse.of(question);
    }
}
