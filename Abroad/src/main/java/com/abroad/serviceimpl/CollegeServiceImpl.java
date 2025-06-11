package com.abroad.serviceimpl;

import com.abroad.entity.College;
import com.abroad.repository.CollegeRepository;
import com.abroad.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeService {
    @Autowired
    private CollegeRepository collegeRepository;

    @Override
    public College saveCollege(College college) {
        return collegeRepository.save(college);
    }

    @Override
    public List<College> getAllColleges() {
        return collegeRepository.findAll();
    }

    @Override
    public List<College> getCollegesByUniversityId(Long universityId) {
        return collegeRepository.findByUniversityId(universityId);
    }
    @Override
    public College updateCollege(Long id, College updatedCollege) {
        College existing = collegeRepository.findById(id).orElse(null);
        if (existing == null) return null;

        if (updatedCollege.getCollegeName() != null) {
            existing.setCollegeName(updatedCollege.getCollegeName());
        }

        if (updatedCollege.getUniversity() != null) {
            existing.setUniversity(updatedCollege.getUniversity());
        }

        return collegeRepository.save(existing);
    }

}
