package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Comment;
import com.codessquad.qna.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepostiory extends CrudRepository<Comment, Long> {

    List<Comment> findAll();

    void deleteAllByPost(Post post);

}
