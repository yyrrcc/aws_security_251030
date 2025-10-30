package com.example.aws_security_251030.repository;

import com.example.aws_security_251030.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 유저 아이디로 찾기
    Optional<User> findByUsername(String username);
}
