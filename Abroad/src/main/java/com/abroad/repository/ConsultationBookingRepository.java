package com.abroad.repository;

import com.abroad.entity.Consultation_Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsultationBookingRepository extends JpaRepository<Consultation_Booking, Long> {
    @Query("SELECT c FROM Consultation_Booking c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
    List<Consultation_Booking> findAllByBranchCode(@Param("branchCode") String branchCode);

}
