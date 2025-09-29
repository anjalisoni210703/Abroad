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

    // Total inquiries grouped by stream
    @Query("SELECT e.stream, COUNT(e) FROM AbroadEnquiry e " +
            "WHERE (:branchCode IS NULL OR e.branchCode = :branchCode) " +
            "GROUP BY e.stream")
    List<Object[]> countInquiriesByStream(@Param("branchCode") String branchCode);

    // Inquiries grouped by stream with date filter
    @Query("SELECT e.stream, COUNT(e) FROM AbroadEnquiry e " +
            "WHERE e.enquiry_date >= :fromDate " +
            "AND (:branchCode IS NULL OR e.branchCode = :branchCode) " +
            "GROUP BY e.stream")
    List<Object[]> countInquiriesByStreamFromDate(@Param("fromDate") LocalDate fromDate,
                                                  @Param("branchCode") String branchCode);

    // Total inquiries grouped by conductBy
    @Query("SELECT e.conductBy, COUNT(e) FROM AbroadEnquiry e " +
            "WHERE (:branchCode IS NULL OR e.branchCode = :branchCode) " +
            "GROUP BY e.conductBy")
    List<Object[]> countInquiriesByConductBy(@Param("branchCode") String branchCode);

    // Inquiries grouped by conductBy with date filter
    @Query("SELECT e.conductBy, COUNT(e) FROM AbroadEnquiry e " +
            "WHERE e.enquiry_date >= :fromDate " +
            "AND (:branchCode IS NULL OR e.branchCode = :branchCode) " +
            "GROUP BY e.conductBy")
    List<Object[]> countInquiriesByConductByFromDate(@Param("fromDate") LocalDate fromDate,
                                                     @Param("branchCode") String branchCode);

    // Count inquiries by stream for a given month
    @Query("SELECT e.stream, COUNT(e) " +
            "FROM AbroadEnquiry e " +
            "WHERE MONTH(e.enquiry_date) = :month " +
            "AND (:branchCode IS NULL OR e.branchCode = :branchCode) " +
            "GROUP BY e.stream")
    List<Object[]> countInquiriesByStreamForMonth(@Param("month") int month,
                                                  @Param("branchCode") String branchCode);

    @Query("SELECT e.courseName, COUNT(e) " +
            "FROM AbroadEnquiry e " +
            "WHERE MONTH(e.enquiry_date) = :month " +
            "AND (:branchCode IS NULL OR e.branchCode = :branchCode) " +
            "GROUP BY e.courseName")
    List<Object[]> countInquiriesByCourseForMonth(@Param("month") int month,
                                                  @Param("branchCode") String branchCode);
    @Query("SELECT e.courseName, COUNT(e), YEAR(e.enquiry_date) " +
            "FROM AbroadEnquiry e " +
            "WHERE YEAR(e.enquiry_date) BETWEEN :startYear AND :endYear " +
            "AND (:branchCode IS NULL OR e.branchCode = :branchCode) " +
            "GROUP BY e.courseName, YEAR(e.enquiry_date)")
    List<Object[]> countInquiriesByCourseForYearRange(@Param("startYear") int startYear,
                                                      @Param("endYear") int endYear,
                                                      @Param("branchCode") String branchCode);



    // Count inquiries by conductBy for a given month
    @Query("SELECT e.conductBy, COUNT(e) " +
            "FROM AbroadEnquiry e " +
            "WHERE MONTH(e.enquiry_date) = :month " +
            "AND (:branchCode IS NULL OR e.branchCode = :branchCode) " +
            "GROUP BY e.conductBy")
    List<Object[]> countInquiriesByConductByForMonth(@Param("month") int month,
                                                     @Param("branchCode") String branchCode);


    // Total enquiries
    @Query("SELECT COUNT(e) FROM AbroadEnquiry e " +
            "WHERE (:branchCode IS NULL OR e.branchCode = :branchCode)")
    Long getAllTotalCountEnquiry(@Param("branchCode") String branchCode);

    // Today enquiries
    @Query("SELECT COUNT(e) FROM AbroadEnquiry e " +
            "WHERE e.enquiry_date >= :fromDate " +
            "AND (:branchCode IS NULL OR e.branchCode = :branchCode)")
    Long getCountFromDate(@Param("fromDate") LocalDate fromDate,
                          @Param("branchCode") String branchCode);

    @Query("SELECT e.status, COUNT(e) " +
            "FROM AbroadEnquiry e " +
            "WHERE (:branchCode IS NULL OR e.branchCode = :branchCode) " +
            "GROUP BY e.status")
    List<Object[]> getAllStatusWiseCount(@Param("branchCode") String branchCode);


    @Query("SELECT e FROM AbroadEnquiry e WHERE " +
            "(:id IS NULL OR e.id = :id) AND " +
            "(:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:email IS NULL OR LOWER(e.email) = LOWER(:email)) AND " +
            "(:phoneNo IS NULL OR e.phone_no = :phoneNo)")
    List<AbroadEnquiry> searchEnquiries(@Param("id") Long id,
                                        @Param("name") String name,
                                        @Param("email") String email,
                                        @Param("phoneNo") Long phoneNo);


    @Query("SELECT e.enquiry_date, COUNT(e) " +
            "FROM AbroadEnquiry e " +
            "WHERE FUNCTION('YEAR', e.enquiry_date) = :year " +
            "AND FUNCTION('MONTH', e.enquiry_date) = :month " +
            "GROUP BY e.enquiry_date " +
            "ORDER BY e.enquiry_date")
    List<Object[]> getDailyInquiryCounts(@Param("year") int year, @Param("month") int month);

    @Query("SELECT e.stream, COUNT(e), YEAR(e.enquiry_date) " +
            "FROM AbroadEnquiry e " +
            "WHERE YEAR(e.enquiry_date) BETWEEN :startYear AND :endYear " +
            "AND (:branchCode IS NULL OR e.branchCode = :branchCode) " +
            "GROUP BY e.stream, YEAR(e.enquiry_date)")
    List<Object[]> countInquiriesByStreamForYearRange(@Param("startYear") int startYear,
                                                      @Param("endYear") int endYear,
                                                      @Param("branchCode") String branchCode);

    // Monthly breakdown for a given year
    @Query("SELECT FUNCTION('YEAR', e.enquiry_date), FUNCTION('MONTH', e.enquiry_date), COUNT(e) " +
            "FROM AbroadEnquiry e " +
            "WHERE FUNCTION('YEAR', e.enquiry_date) BETWEEN :startYear AND :endYear " +
            "GROUP BY FUNCTION('YEAR', e.enquiry_date), FUNCTION('MONTH', e.enquiry_date) " +
            "ORDER BY FUNCTION('YEAR', e.enquiry_date), FUNCTION('MONTH', e.enquiry_date)")
    List<Object[]> getMonthlyInquiryCountsBetweenYears(@Param("startYear") int startYear,
                                                       @Param("endYear") int endYear);

    // All years breakdown
    @Query("SELECT FUNCTION('YEAR', e.enquiry_date), COUNT(e) " +
            "FROM AbroadEnquiry e " +
            "GROUP BY FUNCTION('YEAR', e.enquiry_date) " +
            "ORDER BY FUNCTION('YEAR', e.enquiry_date)")
    List<Object[]> getYearlyInquiryCounts();

    // Get all enquiries by branch code
    List<AbroadEnquiry> findByBranchCode(String branchCode);

    // Get all enquiries by createdByEmail
    List<AbroadEnquiry> findByCreatedByEmail(String createdByEmail);


}
