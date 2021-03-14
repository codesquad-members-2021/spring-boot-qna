package com.codessquad.qna.service;

import com.codessquad.qna.dto.PostDto;
import com.codessquad.qna.entity.Post;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.CommentRepository;
import com.codessquad.qna.repository.PostRepository;
import com.codessquad.qna.util.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public void addPost(Post post) {
        postRepository.save(post);
    }

    public void addPost(PostDto postDto) {
        postRepository.save(Mapper.mapToPost(postDto));
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> {
            return new NotFoundException("해당 게시물을 찾을 수 없습니다.");
        });
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public void updatePost(Long id, PostDto postDto) {
        Post oldPost = getPost(id);
        oldPost.change(Mapper.mapToPost(postDto));
        postRepository.save(oldPost);
    }

    @Transactional
    public void deletePost(Post post, User sessionUser) throws IllegalAccessException {
        if (!post.isMatchedAuthor(sessionUser)) {
            throw new IllegalAccessException("다른 사람의 글을 삭제할 수 없습니다");
        }
        post.delete();
        commentRepository.deactiveCommentsByPostId(post.getPostId());
        postRepository.save(post);
    }

}
