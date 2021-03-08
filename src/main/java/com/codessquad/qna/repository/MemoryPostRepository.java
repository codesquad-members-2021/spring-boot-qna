package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Post;
import com.codessquad.qna.exception.CanNotFindPostException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MemoryPostRepository implements PostRepository {

    private List<Post> posts = Collections.synchronizedList(new ArrayList<>());

    public MemoryPostRepository(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public void save(Post post) {
        posts.add(post);
    }

    @Override
    public Optional<Post> findById(Long postId) {
        Optional<Post> post = Optional.empty();
        try{
            post = Optional.ofNullable(posts.get(postId.intValue()));
        }catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
        return post;
    }

    @Override
    public List<Post> findAll() {
        return Collections.unmodifiableList(posts);
    }

    @Override
    public int size() {
        return posts.size();
    }

}
