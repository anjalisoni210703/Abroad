package com.abroad.serviceimpl;

import com.abroad.entity.Continent;
import com.abroad.entity.Course;
import com.abroad.entity.Registration;
import com.abroad.repository.ContinentRepository;
import com.abroad.repository.CourseRepository;
import com.abroad.repository.RegistrationRepository;
import com.abroad.service.PermissionService;
import com.abroad.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private RegistrationRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Registration createRegistration(Registration registration, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Registration");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);

        if (image != null && !image.isEmpty()) {
            registration.setImage(image.getOriginalFilename());
        }

        registration.setCreatedByEmail(email);
        registration.setRole(role);
        registration.setBranchCode(branchCode);

        return repository.save(registration);
    }

    @Override
    public List<Registration> getAllRegistrations(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Registrations");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public Registration getRegistrationById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Registration");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
    }

    @Override
    public Registration updateRegistration(Long id, Registration registration, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Registration");
        }

        Registration existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        existing.setFirstName(registration.getFirstName() != null ? registration.getFirstName() : existing.getFirstName());
        existing.setLastName(registration.getLastName() != null ? registration.getLastName() : existing.getLastName());
        existing.setMobileNumber(registration.getMobileNumber() != null ? registration.getMobileNumber() : existing.getMobileNumber());
        existing.setEmail(registration.getEmail() != null ? registration.getEmail() : existing.getEmail());
        existing.setPassword(registration.getPassword() != null ? registration.getPassword() : existing.getPassword());
        existing.setContinent(registration.getContinent() != null ? registration.getContinent() : existing.getContinent());
        existing.setCourse(registration.getCourse() != null ? registration.getCourse() : existing.getCourse());

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
