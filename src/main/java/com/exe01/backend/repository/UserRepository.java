package com.exe01.backend.repository;

import com.exe01.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailAndPasswordAndRole(String email, String password, String role);

    Optional<User> findByEmailAndRole(String email, String role);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id = :userId AND  ((:currentDate BETWEEN u.startDateSubscription AND u.expiryDateSubscription) OR u.subscription.planType = 'Free Tee')")
    Optional<User> checkUserSubscription(@Param("userId") UUID userId, @Param("currentDate") Date currentDate);

    List<User> findAll();
}
