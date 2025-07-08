package com.abroad.Service;

import com.abroad.Entity.AbroadContinent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContinentService {
    AbroadContinent createContinent(AbroadContinent abroadContinent, String role, String email);
    List<AbroadContinent> getAllContinents(String role, String email);
    AbroadContinent getContinentById(Long id, String role, String email);
    AbroadContinent updateContinent(Long id, AbroadContinent abroadContinent, String role, String email);
    void deleteContinent(Long id, String role, String email);
}
