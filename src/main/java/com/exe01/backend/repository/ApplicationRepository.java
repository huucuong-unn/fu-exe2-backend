package com.exe01.backend.repository;

import com.exe01.backend.entity.Application;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    @Query("SELECT a FROM Application a " +
            "WHERE (:status = '' OR :status IS NULL OR a.status = :status) " +
            "AND a.student.id = :studentId " +
            "ORDER BY " +
            "CASE WHEN :createdDate = 'asc' THEN a.createdDate END ASC, " +
            "CASE WHEN :createdDate = 'desc' THEN a.createdDate END DESC, " +
            "a.createdDate DESC")
    List<Application> findByStudentIdAndSearchSort(@Param("studentId") UUID studentId,
                                      @Param("status") String status,
                                      @Param("createdDate") String createdDate,
                                      Pageable pageable);
    List<Application> findAllByOrderByCreatedDate(Pageable pageable);

    List<Application> findAllByStatusOrderByCreatedDate(String status, Pageable pageable);

    @Query("SELECT a FROM Application a " +
            "WHERE (:status = 'ALL' OR a.status = :status) " +
            "ORDER BY " +
            "CASE WHEN :createdDate = 'asc' THEN a.createdDate END ASC, " +
            "CASE WHEN :createdDate = 'desc' THEN a.createdDate END DESC, " +
            "a.createdDate DESC")
    List<Application> findAllByMentorIdAndStatusAndSortByCreatedDate(
            @Param("status") String status,
            @Param("createdDate") String createdDate,
            Pageable pageable
    );
    int countByStatus(String status);


    @Query("SELECT MONTH(a.createdDate) AS month, COUNT(a.id) AS applicationCount " +
            "FROM Application a " +
            "GROUP BY MONTH(a.createdDate) " )
            List<Object[]> getApplicationCountByMonth();
}
