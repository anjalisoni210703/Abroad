package com.abroad.serviceimpl;

import com.abroad.entity.Continent;
import com.abroad.entity.Course;
import com.abroad.entity.Registration;
import com.abroad.repository.ContinentRepository;
import com.abroad.repository.CourseRepository;
import com.abroad.repository.RegistrationRepository;
import com.abroad.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private ContinentRepository continentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Registration saveRegistration(Registration registration) {
        Long continentId = registration.getContinent() != null ? registration.getContinent().getId() : null;
        Long courseId = registration.getCourse() != null ? registration.getCourse().getId() : null;

        if (continentId != null) {
            registration.setContinent(continentRepository.findById(continentId)
                    .orElseThrow(() -> new RuntimeException("Continent not found with id: " + continentId)));
        }

        if (courseId != null) {
            registration.setCourse(courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId)));
        }

        return registrationRepository.save(registration);
    }


    @Override
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    @Override
    public Optional<Registration> getRegistrationById(Long id) {
        return registrationRepository.findById(id);
    }

    @Override
    public Registration updateRegistration(Long id, Registration updated) {
        Registration existing = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with id: " + id));

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setMobileNumber(updated.getMobileNumber());
        existing.setEmail(updated.getEmail());
        existing.setPassword(updated.getPassword());

        // Get and set Continent if present
        if (updated.getContinent() != null && updated.getContinent().getId() != null) {
            Continent continent = continentRepository.findById(updated.getContinent().getId())
                    .orElseThrow(() -> new RuntimeException("Continent not found"));
            existing.setContinent(continent);
        }

        // Get and set Course if present
        if (updated.getCourse() != null && updated.getCourse().getId() != null) {
            Course course = courseRepository.findById(updated.getCourse().getId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            existing.setCourse(course);
        }

        return registrationRepository.save(existing);
    }

    @Override
    public void deleteRegistration(Long id) {
        registrationRepository.deleteById(id);
    }

}
