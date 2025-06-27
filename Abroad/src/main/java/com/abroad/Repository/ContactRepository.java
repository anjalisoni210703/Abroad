package com.abroad.Repository;

import com.abroad.Entity.AbroadContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<AbroadContact, Long> {
    @Query("SELECT c FROM AbroadContact c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
    List<AbroadContact> findAllByBranchCode(@Param("branchCode") String branchCode);
}
