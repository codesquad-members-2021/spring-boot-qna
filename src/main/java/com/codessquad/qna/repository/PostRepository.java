package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    public void save(Post post);

    public Optional<Post> findById(Long postId);

    public List<Post> findAll();

    public int size();

    public void update(Post oldPost, Post updatePost);

}
