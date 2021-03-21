package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.QuestionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void save(Question question, User user) {
        Question newQuestion = new Question(user.getName(), question.getTitle(), question.getContents());
        questionRepository.save(newQuestion);
    }

    public void modifyQuestion(Question question, Question modifiedQuestion) {
        questionRepository.save(question.updateQuestion(modifiedQuestion));
    }

    public List<Question> listAllQuestions() {
        return questionRepository.findAllByOrderByIdDesc();
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
    }

    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }
}
