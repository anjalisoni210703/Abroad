package com.abroad.Repository;

import com.abroad.Entity.AbroadEnquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EnquiryRepository extends JpaRepository<AbroadEnquiry, Long>, JpaSpecificationExecutor<AbroadEnquiry> {
    @Query("SELECT e FROM AbroadEnquiry e WHERE e.branchCode = :branchCode ORDER BY e.id DESC")
    List<AbroadEnquiry> findAllByBranchCode(@Param("branchCode") String branchCode);

    // Count inquiries grouped by courseName
    // Count grouped by courseName
    @Query("SELECT e.courseName, COUNT(e) FROM AbroadEnquiry e WHERE e.branchCode = :branchCode GROUP BY e.courseName")
    List<Object[]> countInquiriesByCourseNameAndBranch(@Param("branchCode") String branchCode);

    // Count grouped by stream
    @Query("SELECT e.stream, COUNT(e) FROM AbroadEnquiry e WHERE e.branchCode = :branchCode GROUP BY e.stream")
    List<Object[]> countInquiriesByStreamAndBranch(@Param("branchCode") String branchCode);

    @Query("SELECT e.stream, COUNT(e) FROM AbroadEnquiry e GROUP BY e.stream")
    List<Object[]> countInquiriesByStream();


    @Query("SELECT e.courseName, COUNT(e) FROM AbroadEnquiry e GROUP BY e.courseName")
    List<Object[]> countInquiriesByCourseName();

    // Total inquiries grouped by course
    @Query("SELECT e.courseName, COUNT(e) FROM AbroadEnquiry e " +
            "WHERE (:branchCode IS NULL OR e.branchCode = :branchCode) " +
            "GROUP BY e.courseName")
    List<Object[]> countInquiriesByCourse(@Param("branchCode") String branchCode);

    // Inquiries from a given date (used for today, last7, last30, last365)
    @Query("SELECT e.courseName, COUNT(e) FROM AbroadEnquiry e " +
            "WHERE e.enquiry_date >= :fromDate " +
            "AND (:branchCode IS NULL OR e.branchCode = :branchCode) " +
            "GROUP BY e.courseName")
    List<Object[]> countInquiriesByCourseFromDate(@Param("fromDate") LocalDate fromDate,
                                                  @Param("branchCode") String branchCode);
}
