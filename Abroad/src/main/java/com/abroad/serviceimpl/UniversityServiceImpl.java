package com.abroad.serviceimpl;

import com.abroad.entity.University;
import com.abroad.repository.UniversityRepository;
import com.abroad.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {
    @Autowired
    private UniversityRepository universityRepository;

    @Override
    public University saveUniversity(University university) {
        if (university.getColleges() != null) {
            university.getColleges().forEach(college -> college.setUniversity(university));
        }
        return universityRepository.save(university);
    }

    @Override
    public List<University> getAllUniversities() {
        return universityRepository.findAll();
    }

    @Override
    public University getUniversityById(Long id) {
        return universityRepository.findById(id).orElse(null);
    }
    @Override
    public University updateUniversity(Long id, University updatedUniversity) {
        University existing = universityRepository.findById(id).orElse(null);
        if (existing == null) return null;

        if (updatedUniversity.getUniversityName() != null) {
            existing.setUniversityName(updatedUniversity.getUniversityName());
        }

        if (updatedUniversity.getColleges() != null) {
            // Link each college to the existing university
            updatedUniversity.getColleges().forEach(college -> college.setUniversity(existing));
            existing.setColleges(updatedUniversity.getColleges());
        }

        return universityRepository.save(existing);
    }

}
