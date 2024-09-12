package com.exe01.backend.repository;

import com.exe01.backend.entity.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BlogRepository extends JpaRepository<Blog, UUID> {
    @Query("SELECT b FROM Blog b WHERE b.status = 'ACTIVE' ")
    List<Blog> getOutstandingBlog(Pageable pageable);

    @Query("SELECT b FROM Blog b WHERE b.status = 'ACTIVE' " +
    "ORDER BY b.createdDate DESC")
    List<Blog> getBlogListStatusIsActive(Pageable pageable);

    int countByStatus(String status);
}
