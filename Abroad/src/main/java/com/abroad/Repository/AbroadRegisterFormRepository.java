package com.abroad.Repository;

import com.abroad.Entity.AbroadRegisterForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AbroadRegisterFormRepository extends JpaRepository<AbroadRegisterForm, Long> {

    List<AbroadRegisterForm> findAllByBranchCode(String branchCode);
    Optional<AbroadRegisterForm> findByBarcode(String barcode);

    @Query("SELECT a.status, COUNT(a) FROM AbroadAdmissionForm a GROUP BY a.status")
    List<Object[]> findStatusCounts();
    long countByCreatedDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
