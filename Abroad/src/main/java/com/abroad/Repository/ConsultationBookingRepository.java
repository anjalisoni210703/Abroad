package com.abroad.Repository;

import com.abroad.Entity.AbroadConsultation_Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsultationBookingRepository extends JpaRepository<AbroadConsultation_Booking, Long> {
    @Query("SELECT c FROM AbroadConsultation_Booking c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
    List<AbroadConsultation_Booking> findAllByBranchCode(@Param("branchCode") String branchCode);

}
