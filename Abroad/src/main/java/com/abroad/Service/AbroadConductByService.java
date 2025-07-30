package com.abroad.Service;

import com.abroad.Entity.AbroadConductBy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AbroadConductByService {

    AbroadConductBy addConductBy(String role, String email, AbroadConductBy conductBy);

    AbroadConductBy getConductByById(Long id, String role, String email);

    List<AbroadConductBy> getAllConductBy(String role, String email);

    AbroadConductBy updateConductBy(Long id, String role, String email, AbroadConductBy conductBy);

    Void deleteConductBy(Long id, String role, String email);
}
