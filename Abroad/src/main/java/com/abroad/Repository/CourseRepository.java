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

    @Query("SELECT c FROM AbroadCourse c WHERE  c.abroadStream.id = :streamId ORDER BY c.id DESC")
    List<AbroadCourse> findAllByBranchCodeAndStreamId(@Param("streamId") Long streamId);

    Optional<AbroadCourse> findByCourseNameIgnoreCase(String courseName);

    @Query("SELECT c FROM AbroadCourse c " +
            "WHERE (:streamIds IS NULL OR c.abroadStream.id IN :streamIds) " +
            "AND (:universityIds IS NULL OR c.abroadStream.abroadUniversity.id IN :universityIds) " +
            "AND (:countryIds IS NULL OR c.abroadStream.abroadUniversity.abroadCountry.id IN :countryIds) " +
            "AND (:continentIds IS NULL OR c.abroadStream.abroadUniversity.abroadCountry.abroadContinent.id IN :continentIds) " +
            "ORDER BY c.id DESC")
    List<AbroadCourse> filterCourses(@Param("streamIds") List<Long> streamIds,
                                     @Param("universityIds") List<Long> universityIds,
                                     @Param("countryIds") List<Long> countryIds,
                                     @Param("continentIds") List<Long> continentIds);

}
