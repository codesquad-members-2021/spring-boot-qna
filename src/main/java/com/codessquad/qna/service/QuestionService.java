package com.codessquad.qna.service;

import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import com.codessquad.qna.exception.QuestionNotFoundException;
import com.codessquad.qna.web.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * 질문 생성
     */
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    /**
     * 질문 조회
     */
    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(QuestionNotFoundException::new);
    }

    /**
     * 질문 리스트 조회
     */
    public Page<Question> findUnRemovedList(Pageable pageable) {
        int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "id");
        return questionRepository.findAllByDeletedFalse(pageable);
    }

    /**
     * 현재 페이지, 전체 페이지 조회
     */
    public PageUtil getPageUtil(Page<Question> questions) {
        int currentPage = questions.getNumber() + 1;
        int totalPages = questions.getTotalPages();
        return new PageUtil(currentPage, totalPages);
    }

    /**
     * 질문 수정
     */
    @Transactional
    public void update(Question question, Question updateQuestion) {
        question.update(updateQuestion);
    }

    /**
     * 질문 제거
     */
    @Transactional
    public void deleteBy(Long id, Question question) {
        question.delete();
        questionRepository.deleteById(id);
    }
}
