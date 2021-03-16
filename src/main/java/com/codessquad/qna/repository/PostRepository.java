package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {

    Optional<Post> findByPostIdAndDeletedFalse(Long postId);

    List<Post> findByDeletedFalse();

}
