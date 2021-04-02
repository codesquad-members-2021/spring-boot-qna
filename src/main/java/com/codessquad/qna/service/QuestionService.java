package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.Result;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NoQuestionException;
import com.codessquad.qna.exception.NoUserException;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.web.HttpSessionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public void save(User sessionUser, String title, String contents) {
        Question question = new Question(sessionUser, title, contents);
        questionRepository.save(question);
    }

    @Transactional
    public Result delete(Long id, boolean isLogin, User sessionUser) {
        Question question = getQuestionById(id);
        Result result = valid(isLogin, sessionUser, question);
        if (!result.isValid()) {
            return result;
        }
        question.deleted();
        return result;
    }

    @Transactional
    public Page<Question> getQuestionList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() -1
                , pageable.getPageSize());
        return questionRepository.findAllByDeletedIsFalse(pageable);
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

    @Transactional
    public boolean hasQuestionInNextPage(Pageable pageable) {
        Page<Question> saved = getQuestionList(pageable);
        boolean check = saved.hasNext();
        return check;
    }

    @Transactional
    public boolean hasQuestionInPreviousPage(Pageable pageable) {
        Page<Question> saved = getQuestionList(pageable);
        boolean check = saved.hasPrevious();
        return check;
    }

    @Transactional
    public List<Integer> makePages() {
        int pageSize = 5;
        int startPage = 1;

        List<Integer> pages = new ArrayList<>();
        for (int i = startPage; i < ((questionRepository.countAllByDeletedFalse() / pageSize) + 1) + startPage; i++) { // 5 is the size of a page
            pages.add(i);
        }
        return pages;
    }
}
