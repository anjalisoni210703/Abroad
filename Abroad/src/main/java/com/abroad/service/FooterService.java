package com.abroad.service;

import com.abroad.entity.Footer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FooterService {
    Footer createFooter(Footer footer, MultipartFile image, String role, String email);
    List<Footer> getAllFooters(String role, String email);
    Footer getFooterById(Long id, String role, String email);
    Footer updateFooter(Long id, Footer footer, MultipartFile image, String role, String email);
    void deleteFooter(Long id, String role, String email);
}
