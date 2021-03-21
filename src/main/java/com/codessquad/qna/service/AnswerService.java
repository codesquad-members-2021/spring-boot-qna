package com.codessquad.qna.service;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public Optional<Answer> getOneById(long answerId) {
        return answerRepository.findById(answerId);
    }

    public void create(Long id, String contents, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElse(null);
        Answer answer = new Answer(question, contents, loginUser);

        answerRepository.save(answer);
    }

    public void remove(long id) {
        answerRepository.deleteById(id);
    }
    public List<Answer> findAll() {
        return answerRepository.findAll();
    }
}
