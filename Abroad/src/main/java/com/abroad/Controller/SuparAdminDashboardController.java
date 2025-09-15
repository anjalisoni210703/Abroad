package com.abroad.Controller;


import com.abroad.Service.EnquiryService;
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
    private EnquiryService enquiryService;

    @Autowired
    private StreamService streamService;

    @GetMapping("/count/by-course")
    public List<Map<String, Object>> getCountByCourse() {
        return enquiryService.getInquiryCountByCourseAsMap();
    }

    @GetMapping("/count/by-stream")
    public List<Map<String, Object>> getCountByStream() {
        return enquiryService.getInquiryCountByStreamAsMap();
    }




}
