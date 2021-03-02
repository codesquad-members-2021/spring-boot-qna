package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public Post getPost(int postId) {
        return posts.get(postId);
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
