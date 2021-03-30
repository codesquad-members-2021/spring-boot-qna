package com.codessquad.qna.domain.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByDeletedIsFalse();
    Page<Question> findAllByDeletedIsFalse(Pageable pageable);
    int countQuestionByDeletedFalse();
}
