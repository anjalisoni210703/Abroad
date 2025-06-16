package com.abroad.service;

import com.abroad.entity.Stream;

import java.util.List;

public interface StreamService {
    Stream createStream(Stream stream);
    List<Stream> getAllStreams();
    Stream getStreamById(Long id);
    void deleteStream(Long id);
}
