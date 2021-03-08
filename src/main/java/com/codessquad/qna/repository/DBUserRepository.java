package com.codessquad.qna.repository;

import com.codessquad.qna.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DBUserRepository implements UserRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = Optional.ofNullable(entityManager.find(User.class, id));
        return user;
    }

    @Override
    public void update(User oldUserInfo, User updateUserInfo) {
        oldUserInfo.change(updateUserInfo.getUserId(), updateUserInfo.getPassword(), updateUserInfo.getName(), updateUserInfo.getEmail());
    }

    @Override
    public void remove(Long id) {
        findById(id).ifPresent(value -> { entityManager.remove(value);});
    }

    @Override
    public int size() {
        return entityManager.createQuery("SELECT count(u) FROM User u", Integer.class).getSingleResult();
    }

}
