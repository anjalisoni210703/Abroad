package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadCity;
import com.abroad.Entity.AbroadState;
import com.abroad.Repository.CityRepository;
import com.abroad.Repository.StateRepository;
import com.abroad.Service.CityService;
import com.abroad.Service.PermissionService;
import com.abroad.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private S3Service s3Service;

    @Override
    public AbroadCity createCity(AbroadCity city, MultipartFile image, Long stateId, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create City");
        }

        AbroadState state = stateRepository.findById(stateId)
                .orElseThrow(() -> new RuntimeException("State not found"));

        try {
            if (image != null && !image.isEmpty()) {
                String imageUrl = s3Service.uploadImage(image);
                city.setImage(imageUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }

        city.setAbroadState(state);
        city.setCreatedByEmail(email);
        city.setRole(role);

        return cityRepository.save(city);
    }

    @Override
    public List<AbroadCity> getAllCities(String role, String email, Long stateId) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view cities");
        }

        if (stateId != null) {
            return cityRepository.findAllByAbroadStateId(stateId);
        } else {
            return cityRepository.findAll();
        }
    }

    @Override
    public AbroadCity getCityById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view City");
        }

        return cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));
    }

    @Override
    public AbroadCity updateCity(Long id, AbroadCity city, MultipartFile image, Long stateId, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update City");
        }

        AbroadCity existing = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));

        if (stateId != null) {
            AbroadState state = stateRepository.findById(stateId)
                    .orElseThrow(() -> new RuntimeException("State not found"));
            existing.setAbroadState(state);
        }

        existing.setCity(city.getCity() != null ? city.getCity() : existing.getCity());

        if (image != null && !image.isEmpty()) {
            try {
                if (existing.getImage() != null) {
                    s3Service.deleteImage(existing.getImage());
                }
                String newImageUrl = s3Service.uploadImage(image);
                existing.setImage(newImageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Image update failed", e);
            }
        }

        return cityRepository.save(existing);
    }

    @Override
    public void deleteCity(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete City");
        }

        AbroadCity city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));

        cityRepository.delete(city);
    }
}