package com.jhs.loginwithnofront.repository;

import com.jhs.loginwithnofront.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
