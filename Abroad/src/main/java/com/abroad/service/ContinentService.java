package com.abroad.service;

import com.abroad.entity.Continent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContinentService {
    Continent createContinent(Continent continent, MultipartFile image, String role, String email);
    List<Continent> getAllContinents(String role, String email);
    Continent getContinentById(Long id, String role, String email);
    Continent updateContinent(Long id, Continent continent, MultipartFile image, String role, String email);
    void deleteContinent(Long id, String role, String email);
}
