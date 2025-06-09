package com.abroad.service;

import com.abroad.entity.Footer;

import java.util.List;

public interface FooterService {
    Footer createFooter(Footer footer);
    List<Footer> getAllFooters();
    Footer getFooterById(Long id);
    Footer updateFooter(Long id, Footer footer);
    void deleteFooter(Long id);
}
