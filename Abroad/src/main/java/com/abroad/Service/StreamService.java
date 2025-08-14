package com.abroad.Service;

import com.abroad.Entity.AbroadStream;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StreamService {
    AbroadStream createStream(AbroadStream abroadStream, MultipartFile image, String role, String email);
    List<AbroadStream> getAllStreams();
    AbroadStream getStreamById(Long id, String role, String email);
    AbroadStream updateStream(Long id, AbroadStream abroadStream, MultipartFile image, String role, String email);
    void deleteStream(Long id, String role, String email);
}
