package com.abroad.Repository;

import com.abroad.Entity.AbroadUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbroadUserRepository extends JpaRepository<AbroadUser, Long> {
    Optional<AbroadUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
