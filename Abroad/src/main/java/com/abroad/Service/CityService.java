package com.abroad.Service;

import com.abroad.Entity.AbroadCity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CityService {
    AbroadCity createCity(AbroadCity city, MultipartFile image, Long stateId, String role, String email);
    List<AbroadCity> getAllCities(String role, String email, Long stateId);
    AbroadCity getCityById(Long id, String role, String email);
    AbroadCity updateCity(Long id, AbroadCity city, MultipartFile image, Long stateId, String role, String email);
    void deleteCity(Long id, String role, String email);
    List<String> searchCityNames(String name);
}