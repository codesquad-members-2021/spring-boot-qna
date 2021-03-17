package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    Optional<Comment> findByIdAndDeletedFalse(Long id);

}
