package com.abroad.Repository;

import com.abroad.Entity.AbroadEnquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnquiryRepository extends JpaRepository<AbroadEnquiry, Long>, JpaSpecificationExecutor<AbroadEnquiry> {
    @Query("SELECT e FROM AbroadEnquiry e WHERE e.branchCode = :branchCode ORDER BY e.id DESC")
    List<AbroadEnquiry> findAllByBranchCode(@Param("branchCode") String branchCode);
}
