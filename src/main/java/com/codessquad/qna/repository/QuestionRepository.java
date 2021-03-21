package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {
<<<<<<< HEAD
    List<Question> findAllByAndDeletedFalse();

=======
    @Override
    List<Question> findAll();

    @Override
    void deleteById(Long id);

    List<Question> findAllByAndDeletedFalse();

>>>>>>> 9055b4202d9c420f7c0f8159deecd4735089e8e3
    Optional<Question> findByQuestionIdAndDeletedFalse(Long id);
}
