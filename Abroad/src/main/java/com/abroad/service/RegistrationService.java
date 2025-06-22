package com.abroad.service;

import com.abroad.entity.Registration;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface RegistrationService {
    Registration createRegistration(Registration registration, MultipartFile image, String role, String email);
    List<Registration> getAllRegistrations(String role, String email);
    Registration getRegistrationById(Long id, String role, String email);
    Registration updateRegistration(Long id, Registration registration, MultipartFile image, String role, String email);
    void deleteRegistration(Long id, String role, String email);

}
