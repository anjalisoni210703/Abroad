package com.abroad.serviceimpl;

import com.abroad.entity.Footer;
import com.abroad.repository.FooterRepository;
import com.abroad.service.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FooterServiceImpl implements FooterService {
    @Autowired
    private FooterRepository footerRepository;

    @Override
    public Footer createFooter(Footer footer) {
        return footerRepository.save(footer);
    }

    @Override
    public List<Footer> getAllFooters() {
        return footerRepository.findAll();
    }

    @Override
    public Footer getFooterById(Long id) {
        return footerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Footer not found with id " + id));
    }

    @Override
    public Footer updateFooter(Long id, Footer footer) {
        Footer existing = getFooterById(id);
        existing.setTitle(footer.getTitle());
        existing.setFooterColor(footer.getFooterColor());
        existing.setInstagramLink(footer.getInstagramLink());
        existing.setFacebookLink(footer.getFacebookLink());
        existing.setTwitterLink(footer.getTwitterLink());
        existing.setYoutubeLink(footer.getYoutubeLink());
        existing.setWhatsappLink(footer.getWhatsappLink());
        existing.setEmail(footer.getEmail());
        existing.setMobileNumber(footer.getMobileNumber());
        existing.setAddress(footer.getAddress());
        return footerRepository.save(existing);
    }

    @Override
    public void deleteFooter(Long id) {
        Footer footer = getFooterById(id);
        footerRepository.delete(footer);
    }
}
