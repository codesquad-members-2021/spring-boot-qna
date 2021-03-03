package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    public void add(Post post);

    public Optional<Post> getPost(int postId);

    public List<Post> getPosts();

    public int size();

}
