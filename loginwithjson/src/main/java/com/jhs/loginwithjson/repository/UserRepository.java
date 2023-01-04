package com.jhs.loginwithjson.repository;

import com.jhs.loginwithjson.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    Optional<User> findUserByRefreshToken(String refreshToken);
}
