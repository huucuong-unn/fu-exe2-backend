package com.exe01.backend.repository;

import com.exe01.backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
Optional<Payment> findByRefId(Long refId);
}
