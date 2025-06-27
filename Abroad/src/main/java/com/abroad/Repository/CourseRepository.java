package com.abroad.Repository;

import com.abroad.Entity.AbroadCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<AbroadCourse, Long> {
    @Query("SELECT c FROM AbroadCourse c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
    List<AbroadCourse> findAllByBranchCode(@Param("branchCode") String branchCode);

    @Query("SELECT c FROM AbroadCourse c WHERE c.branchCode = :branchCode AND c.abroadStream.id = :streamId ORDER BY c.id DESC")
    List<AbroadCourse> findAllByBranchCodeAndStreamId(@Param("branchCode") String branchCode,
                                                      @Param("streamId") Long streamId);
}
