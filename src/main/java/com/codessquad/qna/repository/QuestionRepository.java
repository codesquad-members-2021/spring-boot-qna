package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    @Override
    List<Question> findAll();

    @Override
    void deleteById(Long id);

    List<Question> findAllByAndDeletedFalse();

    Optional<Question> findByQuestionIdAndDeletedFalse(Long id);
}
