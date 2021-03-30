package com.codessquad.qna.web.repository;

import com.codessquad.qna.web.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAll(Pageable pageable);
}
