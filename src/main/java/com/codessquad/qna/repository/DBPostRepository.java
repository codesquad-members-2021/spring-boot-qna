package com.codessquad.qna.repository;

import com.codessquad.qna.entity.Post;
import com.codessquad.qna.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DBPostRepository implements PostRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public void save(Post post) {
        entityManager.persist(post);
    }

    @Override
    public Optional<Post> findById(Long postId) {
        Optional<Post> post = Optional.ofNullable(entityManager.find(Post.class, postId));
        return post;
    }

    @Override
    public List<Post> findAll() {
        return entityManager.createQuery("SELECT p FROM Post p", Post.class).getResultList();
    }

    @Override
    public int size() {
        return entityManager.createQuery("SELECT count(p) FROM Post p", Long.class).getSingleResult().intValue();
    }

    @Override
    public void update(Post oldPost, Post newPost) {
        oldPost.change(newPost);
    }

    @Override
    public void delete(Post post) {
        entityManager.createQuery("DELETE FROM Comment c where c.post.postId in :postId").setParameter("postId", post.getPostId()).executeUpdate();
        entityManager.createQuery("DELETE FROM Post p where p.postId in :postId").setParameter("postId", post.getPostId()).executeUpdate();
    }

}
