package com.abroad.repository;

import com.abroad.entity.Stream;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StreamRepository extends JpaRepository<Stream, Long> {
    boolean existsByName(String name);
}
