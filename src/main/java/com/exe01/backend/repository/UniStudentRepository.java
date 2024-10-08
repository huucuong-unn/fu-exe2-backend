package com.exe01.backend.repository;

import com.exe01.backend.entity.UniStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UniStudentRepository extends JpaRepository<UniStudent, UUID> {
    Optional<UniStudent> findByUserId(UUID userId);
}
