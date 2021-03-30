package com.codessquad.qna.web.domain.answer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Override
    List<Answer> findAll();

    Optional<Answer> findById(long id);

    List<Answer> findByQuestionId(Long questionId);

}
