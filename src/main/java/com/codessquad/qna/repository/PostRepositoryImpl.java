package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Post;
import com.codessquad.qna.exception.CanNotFindPostException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository{

    private List<Post> posts = Collections.synchronizedList(new ArrayList<>());

    public PostRepositoryImpl(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public void save(Post post) {
        posts.add(post);
    }

    @Override
    public Optional<Post> getPost(int postId) {
        Optional<Post> post = Optional.empty();
        try{
            post = Optional.ofNullable(posts.get(postId));
        }catch (IndexOutOfBoundsException e) {
            throw new CanNotFindPostException();
        }
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
