package com.umssonline.auth.repository;

import com.umssonline.auth.repository.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAccountAndPassword(String account, String password);
}
