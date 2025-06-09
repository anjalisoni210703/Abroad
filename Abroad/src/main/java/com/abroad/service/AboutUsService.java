package com.abroad.service;

import com.abroad.entity.AboutUs;

import java.util.List;

public interface AboutUsService {
    AboutUs createAboutUs(AboutUs aboutUs);
    List<AboutUs> getAllAboutUs();
    AboutUs getAboutUsById(int id);
    AboutUs updateAboutUs(int id, AboutUs aboutUs);
    void deleteAboutUs(int id);
}
