package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.codessquad.qna.HttpSessionUtils.getUserFromSession;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void save(Question question, User user) {
        Question newQuestion = new Question(user.getName(), question.getTitle(), question.getContents());
        questionRepository.save(newQuestion);
    }

    public List<Question> listAllQuestions() {
        return questionRepository.findAllByOrderByIdDesc();
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Question Not Found"));
    }
}
