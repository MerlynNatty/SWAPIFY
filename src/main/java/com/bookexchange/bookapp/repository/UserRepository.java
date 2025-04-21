package com.bookexchange.bookapp.repository;

import com.bookexchange.bookapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
