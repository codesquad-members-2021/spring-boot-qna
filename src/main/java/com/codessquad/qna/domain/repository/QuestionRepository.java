package com.codessquad.qna.domain.repository;

import com.codessquad.qna.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByDeletedFalseOrderByIdDesc();
}
