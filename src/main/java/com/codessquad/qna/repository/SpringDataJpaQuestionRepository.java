package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaQuestionRepository extends JpaRepository<Question, Long>,
    QuestionRepository {

}
