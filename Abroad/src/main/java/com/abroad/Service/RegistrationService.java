package com.abroad.Service;

import com.abroad.Entity.AbroadRegistration;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RegistrationService {
    AbroadRegistration createRegistration(AbroadRegistration abroadRegistration, MultipartFile image, String role, String email);
    List<AbroadRegistration> getAllRegistrations(String role, String email);
    AbroadRegistration getRegistrationById(Long id, String role, String email);
    AbroadRegistration updateRegistration(Long id, AbroadRegistration abroadRegistration, MultipartFile image, String role, String email);
    void deleteRegistration(Long id, String role, String email);

}
