package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepostiory extends CrudRepository<Comment, Long> {

    List<Comment> findAll();

}
