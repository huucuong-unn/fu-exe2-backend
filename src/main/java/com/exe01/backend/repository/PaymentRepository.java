package com.exe01.backend.repository;

import com.exe01.backend.entity.Payment;
import com.exe01.backend.repository.customEntityResponse.PaymentCustomResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByRefId(Long refId);

    @Query("""
        SELECT p.tierName AS tierName, SUM(p.total) AS total 
        FROM Payment p 
        WHERE p.status = 'PAID' 
        AND p.tierName IN ('Silver Tee', 'Golden Tee') 
        GROUP BY p.tierName
    """)
    List<PaymentCustomResponse> findSuccessfulPaymentsByTierName();

    List<Payment> findAll();
}
