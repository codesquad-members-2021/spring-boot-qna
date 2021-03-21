package com.codessquad.qna.web.domain.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {


    @Override
    @Query("SELECT a FROM #{#entityName} a WHERE a.isActive=true")
    List<Answer> findAll();

    @Query("SELECT a FROM #{#entityName} a WHERE a.isActive=true AND a.id=?1")
    Optional<Answer> findById(long id);

    @Query("SELECT a FROM #{#entityName} a WHERE a.isActive=true AND a.question.id=?1")
    List<Answer> findByQuestionId(Long questionId);
}
