package com.abroad.repository;

import com.abroad.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.branchCode = :branchCode ORDER BY a.id DESC")
    List<Address> findAllByBranchCode(@Param("branchCode") String branchCode);

}
