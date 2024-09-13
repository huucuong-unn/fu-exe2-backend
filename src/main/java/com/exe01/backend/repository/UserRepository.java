package com.exe01.backend.repository;

import com.exe01.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailAndPasswordAndRole(String email, String password, String role);

    Optional<User> findByEmailAndRole(String email, String role);

    Optional<User> findByEmail(String email);
}
