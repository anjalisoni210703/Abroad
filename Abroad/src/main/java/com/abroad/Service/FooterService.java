package com.abroad.Service;

import com.abroad.Entity.AbroadFooter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FooterService {
    AbroadFooter createFooter(AbroadFooter abroadFooter, MultipartFile image, String role, String email);
    List<AbroadFooter> getAllFooters(String role, String email);
    AbroadFooter getFooterById(Long id, String role, String email);
    AbroadFooter updateFooter(Long id, AbroadFooter abroadFooter, MultipartFile image, String role, String email);
    void deleteFooter(Long id, String role, String email);
}
