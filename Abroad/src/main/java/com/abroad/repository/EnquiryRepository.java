package com.abroad.repository;

import com.abroad.entity.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry, Long>, JpaSpecificationExecutor<Enquiry> {
    @Query("SELECT e FROM Enquiry e WHERE e.branchCode = :branchCode ORDER BY e.id DESC")
    List<Enquiry> findAllByBranchCode(@Param("branchCode") String branchCode);
}
