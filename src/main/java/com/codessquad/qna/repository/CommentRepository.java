package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Comment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("select c from Comment c where c.deleteFlag = false and c.id = :id")
    Optional<Comment> findById(@Param("id") Long id);

    @Modifying
    @Query("update Comment c set c.deleteFlag = true where c.post.postId = :postId")
    void deactiveCommentsByPostId(@Param("postId") Long id);

}
