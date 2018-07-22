package com.umssonline.auth.repositories;

import com.umssonline.auth.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAccountAndPassword(String account, String password);
}
