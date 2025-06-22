package com.abroad.repository;

import com.abroad.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    @Query("SELECT r FROM Registration r WHERE r.branchCode = :branchCode ORDER BY r.id DESC")
    List<Registration> findAllByBranchCode(@Param("branchCode") String branchCode);

}
