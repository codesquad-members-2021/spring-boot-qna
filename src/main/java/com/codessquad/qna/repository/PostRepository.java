package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPostIdAndDeletedFalse(Long postId);

    List<Post> findByDeletedFalse();

    @Override
    Page<Post> findAll(Pageable pageable);

}
