package com.abroad.Repository;

import com.abroad.Entity.AbroadRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<AbroadRegistration, Long> {
    @Query("SELECT r FROM AbroadRegistration r WHERE r.branchCode = :branchCode ORDER BY r.id DESC")
    List<AbroadRegistration> findAllByBranchCode(@Param("branchCode") String branchCode);

}
