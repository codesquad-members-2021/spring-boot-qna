package com.codessquad.qna.web.repository;

import com.codessquad.qna.web.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    void save(Question question);
    List<Question> findALL();
    Optional<Question> findByIndex(int index);
}
