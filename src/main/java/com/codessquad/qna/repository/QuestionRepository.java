package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
