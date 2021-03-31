package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByDeletedIsFalse(Pageable pageable);

    int countAllByDeletedFalse();
}
