package com.abroad.repository;

import com.abroad.entity.Continent;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface ContinentRepository extends JpaRepository<Continent, Long> {
}
