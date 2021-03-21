package com.codessquad.qna.web.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Override
    @Query("SELECT q FROM #{#entityName} q WHERE q.isActive=true")
    List<Question> findAll();

    @Query("SELECT q FROM #{#entityName} q WHERE q.isActive=true AND q.id=?1")
    Optional<Question> findById(long id);


public interface QuestionRepository extends CrudRepository<Question, Long> {
}
