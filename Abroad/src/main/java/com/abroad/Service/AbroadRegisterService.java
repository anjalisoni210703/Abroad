package com.abroad.Service;

import com.abroad.Entity.AbroadRegister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AbroadRegisterService {
    AbroadRegister createRegister(String role, String email, AbroadRegister register);

    AbroadRegister getById(Long id, String role, String email);

    List<AbroadRegister> getAll(String role, String email);

    AbroadRegister update(Long id, String role, String email, AbroadRegister uregister);

    Void delete(Long id, String role, String email);
}
