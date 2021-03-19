package com.codessquad.qna.web.domain;

import com.codessquad.qna.web.domain.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
