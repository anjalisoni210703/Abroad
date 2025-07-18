package com.abroad.Service;

import com.abroad.Entity.AbroadLead;

import java.util.List;

public interface LeadService {
    AbroadLead createLead(AbroadLead lead, Long continentId, Long countryId, Long courseId);
    List<AbroadLead> getAllLeads(String role, String email);
    AbroadLead getLeadById(Long id, String role, String email);
    AbroadLead updateLead(Long id, AbroadLead lead, Long continentId, Long countryId, Long courseId, String role, String email);
    void deleteLead(Long id, String role, String email);
}
