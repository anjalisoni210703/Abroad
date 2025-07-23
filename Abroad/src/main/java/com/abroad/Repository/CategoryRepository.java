package com.abroad.Repository;

import com.abroad.Entity.AbroadCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<AbroadCategory,Long> {
}
