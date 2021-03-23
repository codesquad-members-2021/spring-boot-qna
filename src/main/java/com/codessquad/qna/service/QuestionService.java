package com.codessquad.qna.service;

import com.codessquad.qna.utils.HttpSessionUtils;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void registerQuestion(Question question) {
        questionRepository.save(question);
    }

    public List<Question> findQuestions(){

        return (List)questionRepository.findAll();
    }

    public Optional<Question> findQuestion(Long id){

        return questionRepository.findById(id);
    }

    public void delete(Long id) {

        questionRepository.deleteById(id);
    }

    public void  hasPermission(HttpSession session, Question question) {

        if(!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("로그인 필요함");
       }

        User loginUser = HttpSessionUtils.getUserFromSession(session);

        if(!question.isSameWriter(loginUser)) {
            throw new IllegalStateException("본인 글만 수정, 삭제 가능");
        }
    }
}
