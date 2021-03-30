package com.codessquad.qna.web.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Override
    List<Question> findAll();

    Optional<Question> findById(long id);

}
