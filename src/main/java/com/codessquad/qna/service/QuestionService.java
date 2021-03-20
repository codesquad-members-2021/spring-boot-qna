package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.type.NotDeleteException;
import com.codessquad.qna.exception.type.NotFoundException;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public void save(Question question, User writer) {
        question.setWriter(writer);
        questionRepository.save(question);
    }

    public Question findById(Long id) {
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

    public void delete(User loginUser, Question findQuestion) {
        ValidUtils.authenticateOfId(loginUser.getUserId(),findQuestion.getWriter().getUserId());
        if (findQuestion.getAnswers().size() > 0) { // 답글있으면 삭제불가
            throw new NotDeleteException();
        }
        if (!findQuestion.isDeleted()) {
            findQuestion.setDeleted(true);
        }
        questionRepository.save(findQuestion); // soft delete
    }

    public void update(User loginUser,Question findQuestion, String title, String contents){
        ValidUtils.authenticateOfId(loginUser.getUserId(),findQuestion.getWriter().getUserId());
        findQuestion.setTitle(title);
        findQuestion.setContents(contents);
        questionRepository.save(findQuestion);
    }

}
