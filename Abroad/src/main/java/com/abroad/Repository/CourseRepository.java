package com.abroad.Repository;

import com.abroad.Entity.AbroadCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<AbroadCourse, Long> {
//    @Query("SELECT c FROM AbroadCourse c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
//    List<AbroadCourse> findAllByBranchCode(@Param("branchCode") String branchCode);

    @Query("SELECT c FROM AbroadCourse c WHERE  c.abroadCollege.id = :collegeId ORDER BY c.id DESC")
    List<AbroadCourse> findAllByBranchCodeAndStreamId(@Param("collegeId") Long streamId);

    Optional<AbroadCourse> findByCourseNameIgnoreCase(String courseName);

//    @Query("SELECT c FROM AbroadCourse c " +
//            "WHERE (:streamIds IS NULL OR c.abroadStream.id IN :streamIds) " +
//            "AND (:collegeIds IS NULL OR c.abroadStream.abroadCollege.id IN :collegeIds) " +
//            "AND (:universityIds IS NULL OR c.abroadStream.abroadCollege.abroadUniversity.id IN :universityIds) " +
//            "AND (:cityIds IS NULL OR c.abroadStream.abroadCollege.abroadUniversity.abroadCity.id IN :cityIds) " +
//            "AND (:stateIds IS NULL OR c.abroadStream.abroadCollege.abroadUniversity.abroadCity.abroadState.id IN :stateIds) " +
//            "AND (:countryIds IS NULL OR c.abroadStream.abroadCollege.abroadUniversity.abroadCity.abroadState.abroadCountry.id IN :countryIds) " +
//            "AND (:continentIds IS NULL OR c.abroadStream.abroadCollege.abroadUniversity.abroadCity.abroadState.abroadCountry.abroadContinent.id IN :continentIds) " +
//            "ORDER BY c.id DESC")
//    List<AbroadCourse> filterCourses(@Param("streamIds") List<Long> streamIds,
//                                     @Param("collegeIds") List<Long> collegeIds,
//                                     @Param("universityIds") List<Long> universityIds,
//                                     @Param("cityIds") List<Long> cityIds,
//                                     @Param("stateIds") List<Long> stateIds,
//                                     @Param("countryIds") List<Long> countryIds,
//                                     @Param("continentIds") List<Long> continentIds);


    Optional<AbroadCourse> findById(Long id);

}
