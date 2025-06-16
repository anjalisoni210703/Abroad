package com.abroad.serviceimpl;

import com.abroad.entity.Stream;
import com.abroad.repository.StreamRepository;
import com.abroad.service.StreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StreamServiceImpl  implements StreamService {
    private final StreamRepository streamRepository;

    @Override
    public Stream createStream(Stream stream) {
        if (streamRepository.existsByName(stream.getName())) {
            throw new RuntimeException("Stream already exists with this name");
        }

        return streamRepository.save(stream);
    }

    @Override
    public List<Stream> getAllStreams() {
        return streamRepository.findAll();
    }

    @Override
    public Stream getStreamById(Long id) {
        return streamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stream not found with id: " + id));
    }

    @Override
    public void deleteStream(Long id) {
        if (!streamRepository.existsById(id)) {
            throw new RuntimeException("Stream not found with id: " + id);
        }
        streamRepository.deleteById(id);
    }
}
