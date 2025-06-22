package com.abroad.repository;

import com.abroad.entity.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface StreamRepository extends JpaRepository<Stream, Long> {
    @Query("SELECT s FROM Stream s WHERE s.branchCode = :branchCode ORDER BY s.id DESC")
    List<Stream> findAllByBranchCode(@Param("branchCode") String branchCode);
}
