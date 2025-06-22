package com.abroad.service;

import com.abroad.entity.Stream;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StreamService {
    Stream createStream(Stream stream, MultipartFile image, String role, String email);
    List<Stream> getAllStreams(String role, String email);
    Stream getStreamById(Long id, String role, String email);
    Stream updateStream(Long id, Stream stream, MultipartFile image, String role, String email);
    void deleteStream(Long id, String role, String email);}
