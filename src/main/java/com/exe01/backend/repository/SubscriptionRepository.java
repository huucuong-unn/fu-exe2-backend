package com.exe01.backend.repository;

import com.exe01.backend.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    Optional<Subscription> findByPlanType(String planType);

    @Query("SELECT s FROM Subscription s " +
            "JOIN User u ON s.id = u.subscription.id " +
            "WHERE u.id = :id ")
    Optional<Subscription> findByUserId(UUID id);
}
