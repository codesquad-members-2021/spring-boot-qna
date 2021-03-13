package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepostiory extends CrudRepository<Comment, Long> {

    void deleteById(Long id);

}
