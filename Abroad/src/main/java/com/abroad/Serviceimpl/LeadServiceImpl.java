package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadContinent;
import com.abroad.Entity.AbroadCountry;
import com.abroad.Entity.AbroadCourse;
import com.abroad.Entity.AbroadLead;
import com.abroad.Repository.AbroadLeadRepository;
import com.abroad.Repository.ContinentRepository;
import com.abroad.Repository.CountryRepository;
import com.abroad.Repository.CourseRepository;
import com.abroad.Service.LeadService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeadServiceImpl implements LeadService {

    @Autowired
    private AbroadLeadRepository leadRepository;

    @Autowired
    private ContinentRepository continentRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadLead createLead(AbroadLead lead, Long continentId, Long countryId, Long courseId) {
//        if (!permissionService.hasPermission(role, email, "POST")) {
//            throw new AccessDeniedException("No permission to create Lead");
//        }

//        lead.setCreatedByEmail(email);
//        lead.setRole(role);

        if (continentId != null) {
            AbroadContinent continent = continentRepository.findById(continentId)
                    .orElseThrow(() -> new RuntimeException("Continent not found"));
            lead.setAbroadContinent(continent);
            lead.setContinent(continent.getContinentname());
        }

        if (countryId != null) {
            AbroadCountry country = countryRepository.findById(countryId)
                    .orElseThrow(() -> new RuntimeException("Country not found"));
            lead.setAbroadCountry(country);
            lead.setCountry(country.getCountry());
        }

        if (courseId != null) {
            AbroadCourse course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            lead.setAbroadCourse(course);
            lead.setCourse(course.getCourseName());
        }

        return leadRepository.save(lead);
    }

    @Override
    public List<AbroadLead> getAllLeads(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view leads");
        }

        return leadRepository.findAll();
    }

    @Override
    public AbroadLead getLeadById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view lead");
        }

        return leadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead not found"));
    }

    @Override
    public AbroadLead updateLead(Long id, AbroadLead lead, Long continentId, Long countryId, Long courseId, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update lead");
        }

        AbroadLead existing = leadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        existing.setName(lead.getName() != null ? lead.getName() : existing.getName());
        existing.setEmail(lead.getEmail() != null ? lead.getEmail() : existing.getEmail());
        existing.setNumber(lead.getNumber() != null ? lead.getNumber() : existing.getNumber());
        existing.setContinent(lead.getContinent() != null ? lead.getContinent() : existing.getContinent());
        existing.setCountry(lead.getCountry() != null ? lead.getCountry() : existing.getCountry());
        existing.setCourse(lead.getCourse() != null ? lead.getCourse() : existing.getCourse());

        if (continentId != null) {
            AbroadContinent continent = continentRepository.findById(continentId)
                    .orElseThrow(() -> new RuntimeException("Continent not found"));
            existing.setAbroadContinent(continent);
        }

        if (countryId != null) {
            AbroadCountry country = countryRepository.findById(countryId)
                    .orElseThrow(() -> new RuntimeException("Country not found"));
            existing.setAbroadCountry(country);
        }

        if (courseId != null) {
            AbroadCourse course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            existing.setAbroadCourse(course);
        }

        return leadRepository.save(existing);
    }

    @Override
    public void deleteLead(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete lead");
        }

        AbroadLead lead = leadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        leadRepository.delete(lead);
    }
}
