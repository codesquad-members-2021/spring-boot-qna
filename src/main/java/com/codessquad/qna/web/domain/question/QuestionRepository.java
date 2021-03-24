package com.codessquad.qna.web.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Override
    @Query("SELECT q FROM #{#entityName} q WHERE q.isActive=true")
    List<Question> findAll();

    @Query("SELECT q FROM #{#entityName} q WHERE q.isActive=true AND q.id=?1")
    Optional<Question> findById(long id);

    @Query("UPDATE #{#entityName} q SET q.isActive=false WHERE q.id=?1")
    @Modifying
    void softDelete(Long id);

    @Override
    @Transactional
    default void delete(Question question) {
        softDelete(question.getId());
    }


}
