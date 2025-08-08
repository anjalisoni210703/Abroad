package com.abroad.Repository;

import com.abroad.Entity.AbroadRegisterForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbroadRegisterFormRepository extends JpaRepository<AbroadRegisterForm, Long> {
    List<AbroadRegisterForm> findAllByBranchCode(String branchCode);
}
