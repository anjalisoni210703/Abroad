package com.abroad.Repository;

import com.abroad.Entity.AbroadStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface StreamRepository extends JpaRepository<AbroadStream, Long> {
//    @Query("SELECT s FROM AbroadStream s WHERE s.branchCode = :branchCode ORDER BY s.id DESC")
//    List<AbroadStream> findAllByBranchCode(@Param("branchCode") String branchCode);

//    @Query("SELECT s FROM AbroadStream s WHERE s.abroadCollege.id = :collegeId ORDER BY s.id DESC")
//    List<AbroadStream> findAllByBranchCodeAndCollegeId(@Param("collegeId") Long universityId);

    Optional<AbroadStream> findByNameIgnoreCase(String name);

    Optional<AbroadStream> findById(Long id);

    // Query to get stream names with their inquiry counts
    @Query("SELECT s.name, COUNT(e.id) FROM AbroadStream s LEFT JOIN s.abroadEnquiries e GROUP BY s.id, s.name")
    List<Object[]> countInquiriesByStream();

}
