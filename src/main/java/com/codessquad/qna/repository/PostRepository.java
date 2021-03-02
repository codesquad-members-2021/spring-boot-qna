package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Post;

import java.util.List;

public interface PostRepository {

    public void add(Post post);

    public Post getPost(int postId);

    public List<Post> getPosts();

    public int size();

}
