package com.sepism.pangu.model.repository;

import com.sepism.pangu.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNickNameOrEmailOrPhoneNumber(String nickName, String email, String phoneNumber);
}
