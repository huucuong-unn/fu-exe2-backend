package com.exe01.backend.repository;

import com.exe01.backend.entity.Business;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BusinessRepository extends JpaRepository<Business, UUID> {
    List<Business> findAllByStatusOrderByCreatedDate(String status, Pageable pageable);

    @Query("SELECT b FROM Business b " +
    "WHERE (LOWER(b.name) LIKE %:name% OR :name IS NULL)" +
    "AND b.status = 'ACTIVE'")
    Business findByName(@Param("name") String name);

    Business findByUserId(UUID userId);
}
