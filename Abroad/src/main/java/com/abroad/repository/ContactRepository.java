package com.abroad.repository;

import com.abroad.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("SELECT c FROM Contact c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
    List<Contact> findAllByBranchCode(@Param("branchCode") String branchCode);
}
