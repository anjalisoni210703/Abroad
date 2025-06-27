package com.abroad.Service;

import com.abroad.Entity.AbroadContact;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContactService {
    AbroadContact createContact(AbroadContact abroadContact, MultipartFile image, String role, String email);
    List<AbroadContact> getAllContacts(String role, String email);
    AbroadContact getContactById(Long id, String role, String email);
    AbroadContact updateContact(Long id, AbroadContact abroadContact, MultipartFile image, String role, String email);
    void deleteContact(Long id, String role, String email);
}
