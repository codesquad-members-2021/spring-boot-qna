package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.type.NotFoundException;
import com.codessquad.qna.exception.type.UnauthorizedException;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by 68936@naver.com on 2021-03-17 오후 11:47
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void save(Question question) {
        question = Optional.ofNullable(question).orElseThrow(IllegalArgumentException::new);
        questionRepository.save(question);
    }

    public Question findById(Long id) {
        ValidUtils.checkIllegalArgumentOf(id);

        Question q = questionRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!q.isDeleted()) {
            return q;
        }
        throw new NotFoundException();
    }

    public List<Question> findAll() {
        Iterable<Question> questions = questionRepository.findAll();
        List<Question> enableQuestions = new ArrayList<>();
        for (Question question : questions) {
            if(!question.isDeleted()) {
                enableQuestions.add(question);
            }
        }
        return enableQuestions;
    }

    public void confirmWriter(User loginUser, Question questionData) {
        loginUser = Optional.ofNullable(loginUser).orElseThrow(IllegalArgumentException::new);
        questionData = Optional.ofNullable(questionData).orElseThrow(IllegalArgumentException::new);

        if (!Objects.equals(loginUser.getName(), questionData.getWriter().getName())) { // 게시글 작성자가 아닌 경우
            throw new UnauthorizedException();
        }
    }

    public void deleteById(Long targetId) {
        Question targetQuestion = findById(targetId);
        if (!targetQuestion.isDeleted()) {
            targetQuestion.setDeleted(true);
        }
        save(targetQuestion); // soft delete
    }

}
