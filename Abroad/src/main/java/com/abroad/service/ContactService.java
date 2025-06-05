package com.abroad.service;

import com.abroad.entity.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    Contact saveContact(Contact contact);
    List<Contact> getAllContacts();
    Optional<Contact> getContactById(Long id);
    Contact updateContact(Long id, Contact contact);
    void deleteContact(Long id);
}
