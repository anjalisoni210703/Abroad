package com.abroad.Service;

import com.abroad.Entity.AbroadStream;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StreamService {
    AbroadStream createStream(AbroadStream abroadStream, MultipartFile image, String role, String email, Long collegeId);
    List<AbroadStream> getAllStreams(String role, String email, String branchCode, Long collegeId);
    AbroadStream getStreamById(Long id, String role, String email);
    AbroadStream updateStream(Long id, AbroadStream abroadStream, MultipartFile image, String role, String email);
    void deleteStream(Long id, String role, String email);
}
