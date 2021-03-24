package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.type.NotDeleteException;
import com.codessquad.qna.exception.type.NotFoundException;
import com.codessquad.qna.exception.type.UnauthorizedException;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.utils.HttpSessionUtils;
import com.codessquad.qna.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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

    public void save(Question question, HttpSession session) {
        User writer = HttpSessionUtils.getLoginUserOf(session);
        question = Optional.ofNullable(question).orElseThrow(IllegalArgumentException::new);
        question.setWriter(writer);
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

    public void delete(Long questionId, HttpSession session) {
        authenticateOfId(questionId,session);
        Question findQuestion = findById(questionId);
        if (findQuestion.getAnswers().size() > 0) { // 답글있으면 삭제불가
            throw new NotDeleteException();
        }
        if (!findQuestion.isDeleted()) {
            findQuestion.setDeleted(true);
        }
        questionRepository.save(findQuestion); // soft delete
    }

    public void update(Long questionId, HttpSession session, String title, String contents){
        ValidUtils.checkIllegalArgumentOf(title, contents);
        authenticateOfId(questionId,session);
        Question findQuestion = findById(questionId);
        findQuestion.setTitle(title);
        findQuestion.setContents(contents);
        questionRepository.save(findQuestion);
    }

    public void authenticateOfId(Long questionId, HttpSession session){
        Question findQuestion = findById(questionId);
        User loginUser = HttpSessionUtils.getLoginUserOf(session);

        if (!Objects.equals(findQuestion.getWriter().getUserId(), loginUser.getUserId())) {
            throw new UnauthorizedException();
        }

    }

}
