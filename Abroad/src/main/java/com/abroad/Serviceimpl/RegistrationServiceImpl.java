package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadRegistration;
import com.abroad.Repository.RegistrationRepository;
import com.abroad.Service.PermissionService;
import com.abroad.Service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private RegistrationRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadRegistration createRegistration(AbroadRegistration abroadRegistration, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Registration");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);

        if (image != null && !image.isEmpty()) {
            abroadRegistration.setImage(image.getOriginalFilename());
        }

        abroadRegistration.setCreatedByEmail(email);
        abroadRegistration.setRole(role);
        abroadRegistration.setBranchCode(branchCode);

        return repository.save(abroadRegistration);
    }

    @Override
    public List<AbroadRegistration> getAllRegistrations(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Registrations");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public AbroadRegistration getRegistrationById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Registration");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
    }

    @Override
    public AbroadRegistration updateRegistration(Long id, AbroadRegistration abroadRegistration, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Registration");
        }

        AbroadRegistration existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        existing.setFirstName(abroadRegistration.getFirstName() != null ? abroadRegistration.getFirstName() : existing.getFirstName());
        existing.setLastName(abroadRegistration.getLastName() != null ? abroadRegistration.getLastName() : existing.getLastName());
        existing.setMobileNumber(abroadRegistration.getMobileNumber() != null ? abroadRegistration.getMobileNumber() : existing.getMobileNumber());
        existing.setEmail(abroadRegistration.getEmail() != null ? abroadRegistration.getEmail() : existing.getEmail());
        existing.setPassword(abroadRegistration.getPassword() != null ? abroadRegistration.getPassword() : existing.getPassword());
        existing.setAbroadContinent(abroadRegistration.getAbroadContinent() != null ? abroadRegistration.getAbroadContinent() : existing.getAbroadContinent());
        existing.setAbroadCourse(abroadRegistration.getAbroadCourse() != null ? abroadRegistration.getAbroadCourse() : existing.getAbroadCourse());

        if (image != null && !image.isEmpty()) {
            existing.setImage(image.getOriginalFilename());
        }

        return repository.save(existing);
    }

    @Override
    public void deleteRegistration(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Registration");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        repository.deleteById(id);
    }
}
