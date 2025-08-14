package com.abroad.Repository;

import com.abroad.Entity.AbroadContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbroadContactUsRepository extends JpaRepository<AbroadContactUs,Long> {
}
