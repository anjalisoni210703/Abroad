package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadContinent;
import com.abroad.Entity.AbroadCountry;
import com.abroad.Repository.ContinentRepository;
import com.abroad.Repository.CountryRepository;
import com.abroad.Service.CountryService;
import com.abroad.Service.PermissionService;
import com.abroad.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ContinentRepository continentRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private S3Service s3Service;

    @Override
    public AbroadCountry createCountry(AbroadCountry country, MultipartFile image, Long continentId, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Country");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        AbroadContinent continent = continentRepository.findById(continentId)
                .orElseThrow(() -> new RuntimeException("Continent not found"));

        // Image upload logic
        try {
            if (image != null && !image.isEmpty()) {
                String imageUrl = s3Service.uploadImage(image);
                country.setImage(imageUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to S3", e);
        }

        country.setAbroadContinent(continent);
        country.setCreatedByEmail(email);
        country.setRole(role);

        return countryRepository.save(country);
    }

    @Override
    public List<AbroadCountry> getAllCountries(String role, String email, Long continentId) {
        if (!permissionService.hasPermission(role, email, "GET"))
            throw new AccessDeniedException("No permission to view countries");

        if (continentId != null) {
            return countryRepository.findAllByBranchCodeAndContinent(continentId);
        } else {
            return countryRepository.findAll();
        }
    }


    @Override
    public AbroadCountry getCountryById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Country");
        }

        return countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found for this branch"));
    }

    @Override
    public AbroadCountry updateCountry(Long id, AbroadCountry country, MultipartFile image, Long continentId, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Country");
        }

        AbroadCountry existing = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found for this branch"));

        if (continentId != null) {
            AbroadContinent continent = continentRepository.findById(continentId)
                    .orElseThrow(() -> new RuntimeException("Continent not found"));
            existing.setAbroadContinent(continent);
        }

        existing.setCountry(country.getCountry() != null ? country.getCountry() : existing.getCountry());

        // Handle image update
        if (image != null && !image.isEmpty()) {
            try {
                if (existing.getImage() != null) {
                    s3Service.deleteImage(existing.getImage());
                }
                String newImageUrl = s3Service.uploadImage(image);
                existing.setImage(newImageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to handle image update", e);
            }
        }

        return countryRepository.save(existing);
    }

    @Override
    public void deleteCountry(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Country");
        }

        AbroadCountry country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found for this branch"));

        countryRepository.delete(country);
    }

    @Override
    public List<String> searchCountryNames(String name) {
        return countryRepository.findCountryNamesByPartialMatch(name);
    }
}