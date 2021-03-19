package com.codessquad.qna.web.domain;

import com.codessquad.qna.web.domain.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswersRepository extends CrudRepository<Answer, Long> {
}
