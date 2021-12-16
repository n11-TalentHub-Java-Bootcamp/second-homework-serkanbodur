package com.example.secondhomework.dao;

import com.example.secondhomework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UserDAO extends JpaRepository<User, Long> {

    User findUserByUsername(String username);
    Boolean existsUserByUsername(String username);
    User findUserByPhone(String phone);
    Boolean existsUserByPhone(String phone);
}
