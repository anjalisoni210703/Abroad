package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadCountry;
import com.abroad.Entity.AbroadState;
import com.abroad.Repository.CountryRepository;
import com.abroad.Repository.StateRepository;
import com.abroad.Service.PermissionService;
import com.abroad.Service.S3Service;
import com.abroad.Service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private S3Service s3Service;

    @Override
    public AbroadState createState(AbroadState state, MultipartFile image, Long countryId, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create State");
        }

        AbroadCountry country = countryRepository.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country not found"));

        try {
            if (image != null && !image.isEmpty()) {
                String imageUrl = s3Service.uploadImage(image);
                state.setImage(imageUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to S3", e);
        }

        state.setAbroadCountry(country);
        state.setCreatedByEmail(email);
        state.setRole(role);

        return stateRepository.save(state);
    }

    @Override
    public List<AbroadState> getAllStates(String role, String email, Long countryId) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view states");
        }

        if (countryId != null) {
            return stateRepository.findAllByAbroadCountryId(countryId);
        } else {
            return stateRepository.findAll();
        }
    }

    @Override
    public AbroadState getStateById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view State");
        }

        return stateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("State not found"));
    }

    @Override
    public AbroadState updateState(Long id, AbroadState state, MultipartFile image, Long countryId, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update State");
        }

        AbroadState existing = stateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("State not found"));

        if (countryId != null) {
            AbroadCountry country = countryRepository.findById(countryId)
                    .orElseThrow(() -> new RuntimeException("Country not found"));
            existing.setAbroadCountry(country);
        }

        existing.setState(state.getState() != null ? state.getState() : existing.getState());

        if (image != null && !image.isEmpty()) {
            try {
                if (existing.getImage() != null) {
                    s3Service.deleteImage(existing.getImage());
                }
                String newImageUrl = s3Service.uploadImage(image);
                existing.setImage(newImageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to update image", e);
            }
        }

        return stateRepository.save(existing);
    }

    @Override
    public void deleteState(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete State");
        }

        AbroadState state = stateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("State not found"));

        stateRepository.delete(state);
    }
}