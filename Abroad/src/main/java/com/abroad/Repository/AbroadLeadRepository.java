package com.abroad.Repository;

import com.abroad.Entity.AbroadLead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbroadLeadRepository extends JpaRepository<AbroadLead, Long> {
}
