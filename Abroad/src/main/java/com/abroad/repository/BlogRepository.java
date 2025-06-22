package com.abroad.repository;

import com.abroad.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    @Query("SELECT b FROM Blog b WHERE b.branchCode = :branchCode ORDER BY b.id DESC")
    List<Blog> findAllByBranchCode(@Param("branchCode") String branchCode);
}
