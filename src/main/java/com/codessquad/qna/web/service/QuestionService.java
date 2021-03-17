package com.codessquad.qna.web.service;

import com.codessquad.qna.web.HttpSessionUtils;
import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void postQuestion(Question question, User user) {
        question.setWriter(user.getUserId());
        questionRepository.save(question);
    }

    public Iterable<Question> findQuestions() {
        return questionRepository.findAll();
    }

    public Question findQuestion(long id) {
        return questionRepository
                .findById(id)
                .orElseThrow(()-> new IllegalStateException("찾는 question이 없습니다"));
    }

    public void updateQuestion(Question originQuestion, Question question) {
        originQuestion.update(question);
        questionRepository.save(originQuestion);
    }

    public Question getOriginQuestion(long id, HttpSession session) {
        Question originQuestion = findQuestion(id);
        if(!originQuestion.isMatchingWriter(HttpSessionUtils.getSessionedUser(session).getUserId())) {
            throw new IllegalStateException("자신의 글만 수정할 수 있습니다");
        }
        return originQuestion;
    }

    public void deleteQuestion(long id) {
        questionRepository.deleteById(id);
    }
}
