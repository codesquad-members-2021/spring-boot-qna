package com.codessquad.qna.web.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends PagingAndSortingRepository<Question, Integer> {

    List<Question> findAllByDeletedOrderByCreatedDateTimeDesc(boolean deleted, Pageable pageable);

    Optional<Question> findByIdAndDeletedFalse(Long id);

    int countAllByDeletedFalse();
}
