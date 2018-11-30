package com.github.masaliev.passport.repository;

import com.github.masaliev.passport.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
