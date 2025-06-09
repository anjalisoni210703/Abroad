package com.abroad.serviceimpl;

import com.abroad.entity.AboutUs;
import com.abroad.repository.AboutUsRepository;
import com.abroad.service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AboutUsServiceImpl implements AboutUsService {
    @Autowired
    private AboutUsRepository aboutUsRepository;

    @Override
    public AboutUs createAboutUs(AboutUs aboutUs) {
        return aboutUsRepository.save(aboutUs);
    }

    @Override
    public List<AboutUs> getAllAboutUs() {
        return aboutUsRepository.findAll();
    }

    @Override
    public AboutUs getAboutUsById(int id) {
        return aboutUsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AboutUs entry not found with id " + id));
    }

    @Override
    public AboutUs updateAboutUs(int id, AboutUs aboutUs) {
        AboutUs existing = getAboutUsById(id);
        existing.setAboutUsTitle(aboutUs.getAboutUsTitle());
        existing.setAboutUsDescription(aboutUs.getAboutUsDescription());
        existing.setAboutUsImage(aboutUs.getAboutUsImage());
        return aboutUsRepository.save(existing);
    }

    @Override
    public void deleteAboutUs(int id) {
        AboutUs aboutUs = getAboutUsById(id);
        aboutUsRepository.delete(aboutUs);
    }

}
