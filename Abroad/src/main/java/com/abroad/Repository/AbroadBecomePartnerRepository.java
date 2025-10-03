package com.abroad.Repository;

import com.abroad.Entity.AbroadBecomePartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbroadBecomePartnerRepository extends JpaRepository<AbroadBecomePartner, Long>, JpaSpecificationExecutor<AbroadBecomePartner> {

//    @Query("SELECT p FROM AbroadBecomePartner p WHERE p.branchCode = :branchCode ORDER BY p.id DESC")
//    List<AbroadBecomePartner> findAllByBranchCode(@Param("branchCode") String branchCode);



        @Query("SELECT p.status, COUNT(p) " +
                "FROM AbroadBecomePartner p " +
                "GROUP BY p.status")
        List<Object[]> getAllStatusWiseCount();

}