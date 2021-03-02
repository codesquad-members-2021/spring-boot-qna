package com.codessquad.qna.service;

import com.codessquad.qna.entity.Post;
import com.codessquad.qna.repository.PostRepository;
import com.codessquad.qna.repository.PostRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    PostRepository postRepository = new PostRepositoryImpl(new ArrayList<>());

    public void addPost(Post post) {
        int index = postRepository.size() + 1;
        post.setPostId(index);
        postRepository.add(post);
    }

    public Post getPost(int id) {
        return postRepository.getPost(id);
    }

    public List<Post> getPosts() {
        return postRepository.getPosts();
    }

}
