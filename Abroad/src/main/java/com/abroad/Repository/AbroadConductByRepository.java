package com.abroad.Repository;

import com.abroad.Entity.AbroadCategory;
import com.abroad.Entity.AbroadConductBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbroadConductByRepository extends JpaRepository<AbroadConductBy,Long> {
}
