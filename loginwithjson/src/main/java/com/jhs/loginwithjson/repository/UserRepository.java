package com.jhs.loginwithjson.repository;

import com.jhs.loginwithjson.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
