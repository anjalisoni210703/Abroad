package com.abroad.Repository;

import com.abroad.Entity.AbroadCollege;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CollegeRepository  extends JpaRepository<AbroadCollege, Long> {
//    @Query("SELECT c FROM AbroadCollege c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
//    List<AbroadCollege> findAllByBranchCode(@Param("branchCode") String branchCode);

    @Query("SELECT c FROM AbroadCollege c WHERE c.abroadUniversity.id = :universityId ORDER BY c.id DESC")
    List<AbroadCollege> findAllByBranchCodeAndUniversityId( @Param("universityId") Long universityId);

    Optional<AbroadCollege> findByCollegeNameIgnoreCase(String collegeName);


    Optional<AbroadCollege> findByCollegeName(String collegeName);
    Page<AbroadCollege> findByAbroadUniversityId(Long universityId, Pageable pageable);
    List<AbroadCollege> findByCollegeNameIn(List<String> collegeNames);

}
