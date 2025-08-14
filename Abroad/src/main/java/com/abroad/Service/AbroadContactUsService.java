package com.abroad.Service;

import com.abroad.Entity.AbroadContactUs;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AbroadContactUsService {
    AbroadContactUs createContactUs(AbroadContactUs contactUs);

    AbroadContactUs getContactUsById(Long id, String role, String email);

    List<AbroadContactUs> getAllContactUs(String role, String email);

    AbroadContactUs update(Long id, AbroadContactUs uContactUs, String role, String email);

    Void delete(Long id, String role, String email);
}
