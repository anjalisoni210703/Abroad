package com.abroad.Repository;

import com.abroad.Entity.AbroadExamPreparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbroadExamPreparationRepository extends JpaRepository<AbroadExamPreparation, Long> {
}