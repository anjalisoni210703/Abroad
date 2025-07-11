package com.abroad.Repository;

import com.abroad.Entity.AbroadExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbroadExamRepository extends JpaRepository<AbroadExam, Long> {
}