package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void create(QuestionDto questionDto) {
        Question question = new Question(questionDto.getWriter(), questionDto.getContents(), questionDto.getTitle());
        questionRepository.save(question);
    }

    public List<Question> findQuestions() {
        return questionRepository.findAll();
    }

    public Question findQuestionById(long id) {
        return questionRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
