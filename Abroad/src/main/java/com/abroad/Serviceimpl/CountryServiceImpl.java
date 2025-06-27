package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadContinent;
import com.abroad.Entity.AbroadCountry;
import com.abroad.Repository.ContinentRepository;
import com.abroad.Repository.CountryRepository;
import com.abroad.Service.CountryService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ContinentRepository continentRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadCountry createCountry(AbroadCountry country, Long continentId, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Country");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        AbroadContinent continent = continentRepository.findById(continentId)
                .orElseThrow(() -> new RuntimeException("Continent not found"));

        country.setAbroadContinent(continent);
        country.setCreatedByEmail(email);
        country.setRole(role);
        country.setBranchCode(branchCode);

        return countryRepository.save(country);
    }

    @Override
    public List<AbroadCountry> getAllCountries(String role, String email, String branchCode) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Countries");
        }

        return countryRepository.findAllByBranchCode(branchCode);
    }

    @Override
    public AbroadCountry getCountryById(Long id, String role, String email, String branchCode) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Country");
        }

        return countryRepository.findByIdAndBranchCode(id, branchCode)
                .orElseThrow(() -> new RuntimeException("Country not found for this branch"));
    }

    @Override
    public AbroadCountry updateCountry(Long id, AbroadCountry country, Long continentId, MultipartFile image, String role, String email, String branchCode) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Country");
        }

        AbroadCountry existing = countryRepository.findByIdAndBranchCode(id, branchCode)
                .orElseThrow(() -> new RuntimeException("Country not found for this branch"));

        if (continentId != null) {
            AbroadContinent continent = continentRepository.findById(continentId)
                    .orElseThrow(() -> new RuntimeException("Continent not found"));
            existing.setAbroadContinent(continent);
        }

        existing.setCountry(country.getCountry() != null ? country.getCountry() : existing.getCountry());

        return countryRepository.save(existing);
    }

    @Override
    public void deleteCountry(Long id, String role, String email, String branchCode) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Country");
        }

        AbroadCountry country = countryRepository.findByIdAndBranchCode(id, branchCode)
                .orElseThrow(() -> new RuntimeException("Country not found for this branch"));

        countryRepository.delete(country);
    }
}