package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.Result;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NoQuestionException;
import com.codessquad.qna.exception.NoUserException;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.web.HttpSessionUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public void save(User sessionUser, Question question) {
        question.setWriter(sessionUser);
        questionRepository.save(question);
    }

    @Transactional
    public Result delete(Long id, boolean isLogin, User sessionUser) {
        Question question = getQuestionById(id);
        Result result = valid(isLogin, sessionUser, question);
        if (!result.isValid()) {
            return result;
        }
        question.delete();
        return result;
    }

    public List<Question> getQuestionList() {
        return questionRepository.findAllByDeletedIsFalse();
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(NoQuestionException::new);
    }
    
    @Transactional
    public void updateQuestion(Long id, Question updateQuestion) {
        Question question = getQuestionById(id);
        question.update(updateQuestion);
    }

    public Result valid(boolean isLoginUser, User sessionUser, Question question) {
        if (!isLoginUser) {
            return Result.fail("로그인을 먼저 진행해주세요.");
        }

        if (!question.isMatchingWriter(sessionUser)) {
            return Result.fail("수정할 수 있는 권한이 없습니다.");
        }

        return Result.ok();
    }

    public Result valid(boolean isLoginUser) {
        if (!isLoginUser) {
            return Result.fail("로그인을 먼저 진행해주세요.");
        }
        return Result.ok();
    }

}
