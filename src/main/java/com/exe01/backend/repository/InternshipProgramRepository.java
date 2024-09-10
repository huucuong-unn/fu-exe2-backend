package com.exe01.backend.repository;

import com.exe01.backend.entity.InternshipProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InternshipProgramRepository extends JpaRepository<InternshipProgram, UUID> {
    @Query("SELECT ip FROM InternshipProgram ip " +
            "JOIN Application a ON ip.id = a.id " +
            "WHERE ip.status = 'OPEN' " +
            "AND FUNCTION('MONTH', a.createdDate) = FUNCTION('MONTH', CURRENT_DATE) " +
            "AND FUNCTION('YEAR', a.createdDate) = FUNCTION('YEAR', CURRENT_DATE) " +
            "GROUP BY ip.id " +
            "ORDER BY COUNT(a.id) DESC")
    List<InternshipProgram> findTop3InternshipProgramsByApplications();
}
