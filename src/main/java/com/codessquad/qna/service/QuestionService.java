package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.UnauthorizedAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final int NUM_PER_PAGE = 15;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> questions() {
        return questionRepository.findAllByDeletedFalse();
    }

    public void register(Question question, User loginUser) {
        question.setWriter(loginUser);
        questionRepository.save(question);
    }

    public Question question(Long id) {
        return questionRepository.findByIdAndDeletedFalse(id).orElseThrow(NotFoundException::new);
    }

    public Question questionWithAuthentication(Long id, User loginUser) {
        Question question = this.question(id);
        if (!question.isWriter(loginUser)) {
            throw new UnauthorizedAccessException("다른 사람의 질문을 수정하거나 삭제할 수 없습니다.");
        }
        return question;
    }

    public void update(Long id, User loginUser, Question updatingQuestion) {
        Question question = questionWithAuthentication(id, loginUser);
        question.updateContents(updatingQuestion);
        questionRepository.save(question);
    }

    @Transactional
    public void delete(Long id, User loginUser) {
        Question question = questionWithAuthentication(id, loginUser);
        question.delete(loginUser);
    }

    public Page<Question> questionsPage(int pageNumber) {
        int pageIndex = pageNumber - 1;
        Page<Question> page = questionRepository.findAllByDeletedFalse(
                PageRequest.of(pageIndex, NUM_PER_PAGE, Sort.by("createdDateTime").descending()));
        if (page.getTotalPages() < pageNumber) {
            throw new NotFoundException();
        }
        return page;
    }


}
