package com.codessquad.qna.service;

import com.codessquad.qna.entity.Question;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.QuestionNotFoundException;
import com.codessquad.qna.repository.QuestionRepository;
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

    public void addQuestion(Question question) {
        questionRepository.save(question);
    }

    public void updateQuestion(long questionId, String title, String contents, User tryUser) {
        Question question = getQuestion(questionId);
        if (!question.getWriter().verify(tryUser)) {
            throw new IllegalStateException("자신의 글만 수정할 수 있습니다.");
        }
        question.update(title, contents);
        questionRepository.save(question);
        return;
    }

    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }

    public Question getQuestion(long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
    }
}
