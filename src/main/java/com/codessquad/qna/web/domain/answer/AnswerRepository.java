package com.codessquad.qna.web.domain.answer;

import com.codessquad.qna.web.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Override
    @Query("SELECT a FROM #{#entityName} a WHERE a.isActive=true")
    List<Answer> findAll();

    @Query("SELECT a FROM #{#entityName} a WHERE a.isActive=true AND a.id=?1")
    Optional<Answer> findById(long id);

    @Query("SELECT a FROM #{#entityName} a WHERE a.isActive=true AND a.question.id=?1")
    List<Answer> findByQuestionId(Long questionId);

    @Query("UPDATE #{#entityName} a SET a.isActive=false WHERE a.id=?1")
    @Modifying
    void softDelete(Long id);

    @Override
    @Transactional
    default void delete(Answer answer) {
        softDelete(answer.getId());
    }

}
