package com.sepism.pangu.model.service;

import com.sepism.pangu.model.repository.UserRepository;
import com.sepism.pangu.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional(readOnly = true)
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    public User findById(Long id){
        return userRepository.findOne(id);
    }
    public User save(User user){
        return userRepository.save(user);
    }
}
