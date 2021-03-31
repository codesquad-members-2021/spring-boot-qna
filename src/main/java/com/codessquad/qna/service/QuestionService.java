package com.codessquad.qna.service;

import com.codessquad.qna.domain.pagination.Criteria;
import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.answer.AnswerRepository;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.question.QuestionRepository;
import com.codessquad.qna.exception.QuestionNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Transactional
    public Long create(Question question) {
        return questionRepository.save(question).getId();
    }

    @Transactional
    public Long update(Long id, Question questionWithUpdatedInfo) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        question.update(questionWithUpdatedInfo);
        return id;
    }

    @Transactional
    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
    }

    @Transactional
    public List<Question> findAll() {
        return questionRepository.findAllByDeletedIsFalse();
    }

    @Transactional
    public void deleteById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        List<Answer> answers = answerRepository.findAllByQuestionIdAndDeletedIsFalse(id);
        answers.stream().allMatch(question::isAnsweredYourself);
        question.delete();
    }

    public Page<Question> pagingList(Criteria criteria) {
        Pageable pageRequest = PageRequest.of(criteria.getPageNum() - 1, criteria.getSize(), Sort.by("id").descending());
        return questionRepository.findAllByDeletedIsFalse(pageRequest);
    }
}
