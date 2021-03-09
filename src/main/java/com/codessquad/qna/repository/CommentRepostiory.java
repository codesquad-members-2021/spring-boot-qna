package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepostiory extends CrudRepository<Comment, Long> {

}
