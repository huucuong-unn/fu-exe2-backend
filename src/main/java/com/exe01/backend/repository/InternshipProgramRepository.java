package com.exe01.backend.repository;

import com.exe01.backend.entity.InternshipProgram;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InternshipProgramRepository extends JpaRepository<InternshipProgram, UUID> {
    @Query("SELECT ip FROM InternshipProgram ip " +
            "JOIN Application a ON ip.id = a.internshipProgram.id " +
            "WHERE ip.status = 'OPEN' " +
            "AND FUNCTION('MONTH', a.createdDate) = FUNCTION('MONTH', CURRENT_DATE) " +
            "AND FUNCTION('YEAR', a.createdDate) = FUNCTION('YEAR', CURRENT_DATE) " +
            "GROUP BY ip.id " +
            "ORDER BY COUNT(a.id) DESC " +
            "LIMIT 3")
    List<InternshipProgram> findTop3InternshipProgramsByApplications();

    @Query("SELECT ip FROM InternshipProgram ip " +
            "WHERE ip.status = 'OPEN' ")
    List<InternshipProgram> getAllInternshipProgramLimit4(Pageable pageable);

    @Query("SELECT ip FROM InternshipProgram ip " +
            "JOIN ip.business b " +
            "WHERE (LOWER(ip.skillsAndKeywordRelate) LIKE %:keyword% " +
            "OR LOWER(ip.titleName) LIKE %:keyword% OR LOWER(b.name) LIKE %:keyword%) " +
            "AND (LOWER(ip.location) LIKE :location OR :location = 'All city') " +
            "AND ip.status = 'OPEN' " +
            "ORDER BY ip.createdDate DESC")
    List<InternshipProgram> getInternshipPrograms(@Param("keyword") String keyword
                                                , @Param("location") String location
                                                , Pageable pageable);

    @Query("SELECT COUNT(ip) FROM InternshipProgram ip " +
            "JOIN ip.business b " +
            "WHERE (LOWER(ip.skillsAndKeywordRelate) LIKE %:keyword% " +
            "OR LOWER(ip.titleName) LIKE %:keyword% OR LOWER(b.name) LIKE %:keyword%) " +
            "AND (LOWER(ip.location) LIKE :location OR :location = 'All city') " +
            "AND ip.status = 'OPEN' " +
            "ORDER BY ip.createdDate DESC")
    int countBySearchSort(@Param("keyword") String keyword
            , @Param("location") String location);

    @Query("SELECT ip FROM InternshipProgram ip " +
    "WHERE ip.business.id = :businessId AND (ip.status = 'OPEN' OR ip.status = 'CLOSE') " +
    "ORDER BY ip.createdDate DESC")
    List<InternshipProgram> getLastActivitiesByBusinessId(@Param("businessId") UUID businessId, Pageable pageable);

    int countByStatus(String status);
}
