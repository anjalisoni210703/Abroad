package com.abroad.Service;

import com.abroad.Entity.AbroadCountry;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CountryService {
    AbroadCountry createCountry(AbroadCountry country, Long continentId, MultipartFile image, String role, String email);
    List<AbroadCountry> getAllCountries(String role, String email, String branchCode, Long continentId);
    AbroadCountry getCountryById(Long id, String role, String email, String branchCode);
    AbroadCountry updateCountry(Long id, AbroadCountry country, Long continentId, MultipartFile image, String role, String email, String branchCode);
    void deleteCountry(Long id, String role, String email, String branchCode);
}