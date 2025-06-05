package com.abroad.service;

import com.abroad.entity.Registration;

import java.util.List;
import java.util.Optional;

public interface RegistrationService {
    Registration saveRegistration(Registration registration);
    List<Registration> getAllRegistrations();
    Optional<Registration> getRegistrationById(Long id);
    Registration updateRegistration(Long id, Registration registration);
    void deleteRegistration(Long id);
}
