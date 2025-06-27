package com.abroad.Repository;

import com.abroad.Entity.AbroadStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface StreamRepository extends JpaRepository<AbroadStream, Long> {
    @Query("SELECT s FROM AbroadStream s WHERE s.branchCode = :branchCode ORDER BY s.id DESC")
    List<AbroadStream> findAllByBranchCode(@Param("branchCode") String branchCode);

    @Query("SELECT s FROM AbroadStream s WHERE s.branchCode = :branchCode AND s.abroadCollege.id = :collegeId ORDER BY s.id DESC")
    List<AbroadStream> findAllByBranchCodeAndCollegeId(@Param("branchCode") String branchCode,
                                                       @Param("collegeId") Long collegeId);
}
