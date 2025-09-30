package com.abroad.Repository;

import com.abroad.Entity.AbroadCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<AbroadCourse, Long> {
//    @Query("SELECT c FROM AbroadCourse c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
//    List<AbroadCourse> findAllByBranchCode(@Param("branchCode") String branchCode);

    @Query("SELECT c FROM AbroadCourse c WHERE  c.abroadCollege.id = :collegeId ORDER BY c.id DESC")
    List<AbroadCourse> findAllByBranchCodeAndStreamId(@Param("collegeId") Long streamId);

    Optional<AbroadCourse> findByCourseNameIgnoreCase(String courseName);
    Optional<AbroadCourse> findById(Long id);
    Page<AbroadCourse> findByAbroadCollegeId(Long collegeId, Pageable pageable);

    List<AbroadCourse> findByCourseNameIn(List<String> courseNames);
    List<AbroadCourse> findByStreamNameIn(List<String> streamNames);
    @Query("SELECT c FROM AbroadCourse c WHERE LOWER(CAST(c.scholarship AS string)) = LOWER(:scholarship)")
    List<AbroadCourse> findByScholarshipIgnoreCase(@Param("scholarship") String scholarship);
    List<AbroadCourse> findByExamTypeIn(List<String> examTypes);
    // ✅ New filters
    @Query("SELECT c FROM AbroadCourse c WHERE LOWER(c.englishExamRequirements) = LOWER(:englishExamRequirements)")
    List<AbroadCourse> findByEnglishExamRequirementsIgnoreCase(@Param("englishExamRequirements") String englishExamRequirements);

    @Query("SELECT c FROM AbroadCourse c WHERE c.academicRequirements IN :academicRequirements")
    List<AbroadCourse> findByAcademicRequirementsIn(@Param("academicRequirements") List<String> academicRequirements);

    List<AbroadCourse> findByIntakeIn(List<String> intake);

}
