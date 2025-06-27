package com.abroad.Repository;

import com.abroad.Entity.AbroadBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<AbroadBlog, Long> {
    @Query("SELECT b FROM AbroadBlog b WHERE b.branchCode = :branchCode ORDER BY b.id DESC")
    List<AbroadBlog> findAllByBranchCode(@Param("branchCode") String branchCode);
}
