package com.abroad.service;

import com.abroad.entity.Contact;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    Contact createContact(Contact contact, MultipartFile image, String role, String email);
    List<Contact> getAllContacts(String role, String email);
    Contact getContactById(Long id, String role, String email);
    Contact updateContact(Long id, Contact contact, MultipartFile image, String role, String email);
    void deleteContact(Long id, String role, String email);
}
