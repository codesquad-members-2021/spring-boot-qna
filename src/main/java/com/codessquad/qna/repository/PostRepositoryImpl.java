package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PostRepositoryImpl implements PostRepository{

    private List<Post> posts;

    public PostRepositoryImpl(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public void add(Post post) {
        posts.add(post);
    }

    @Override
    public Optional<Post> getPost(int postId) {
        Optional<Post> post = Optional.ofNullable(posts.get(postId));
        return post;
    }

    @Override
    public List<Post> getPosts() {
        return Collections.unmodifiableList(posts);
    }

    @Override
    public int size() {
        return posts.size();
    }
}
