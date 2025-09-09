package com.abroad.Controller;


import com.abroad.Service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
@RequestMapping("/dashboard")
public class SuparAdminDashboardController {

    @Autowired
    private StreamService streamService;

    @GetMapping("/inquiries-by-stream")
    public ResponseEntity<?> getInquiriesByStreamCountAsMap() {
        try {
            List<Map<String, Object>> inquiryCounts = streamService.getInquiryCountByStreamAsMap();
            return ResponseEntity.ok(inquiryCounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch inquiry counts: " + e.getMessage()));
        }
    }




}
