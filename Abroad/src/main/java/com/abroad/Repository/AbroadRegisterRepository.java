package com.abroad.Repository;

import com.abroad.Entity.AbroadRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbroadRegisterRepository extends JpaRepository< AbroadRegister,Long> {
}
