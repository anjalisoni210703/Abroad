package com.abroad.controller;

import com.abroad.entity.Stream;
import com.abroad.service.StreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StreamController {
    private  StreamService streamService;

    @PostMapping("/createStream")
    public ResponseEntity<Stream> createStream(@RequestBody Stream stream) {
        Stream savedStream = streamService.createStream(stream);
        return ResponseEntity.ok(savedStream);
    }

    @GetMapping("/getAllStreams")
    public ResponseEntity<List<Stream>> getAllStreams() {
        return ResponseEntity.ok(streamService.getAllStreams());
    }

    @GetMapping("/getStreamById/{id}")
    public ResponseEntity<Stream> getStreamById(@PathVariable Long id) {
        return ResponseEntity.ok(streamService.getStreamById(id));
    }

    @DeleteMapping("/deleteStream/{id}")
    public ResponseEntity<String> deleteStream(@PathVariable Long id) {
        streamService.deleteStream(id);
        return ResponseEntity.ok("Stream deleted successfully");
    }
}
