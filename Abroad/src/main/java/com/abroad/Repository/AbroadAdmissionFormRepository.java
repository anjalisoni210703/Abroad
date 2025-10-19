package com.abroad.Repository;

import com.abroad.DTO.AdmissionFormPersonalAcademicDTO;
import com.abroad.Entity.AbroadAdmissionForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AbroadAdmissionFormRepository extends JpaRepository<AbroadAdmissionForm, Long> {
    List<AbroadAdmissionForm> findByBranchCode(String branchCode);

    @Query("SELECT new com.abroad.DTO.AdmissionFormPersonalAcademicDTO(" +
            "f.fullName, f.email, f.phone, f.country, f.university, f.course) " +
            "FROM AbroadAdmissionForm f WHERE f.id = :id")
    AdmissionFormPersonalAcademicDTO findPersonalAcademicById(@Param("id") Long id);

    List<AbroadAdmissionForm> findByCreatedByEmail(String createdByEmail);


    @Query("SELECT a.status, COUNT(a) FROM AbroadAdmissionForm a GROUP BY a.status")
    List<Object[]> findStatusCounts();

    long countByCreatedDateTimeBetween(LocalDateTime start, LocalDateTime end);
}