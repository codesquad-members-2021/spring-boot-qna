package com.codessquad.qna.answer;

import com.codessquad.qna.question.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findAllByQuestionIdAndDeletedFalse(Long questionId);
    int countByQuestionAndDeletedFalse(Question question);
}
