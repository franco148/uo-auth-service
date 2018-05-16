package com.umssonline.auth.repositories;

import com.umssonline.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByAccountAndPassword(String account, String password);
}
