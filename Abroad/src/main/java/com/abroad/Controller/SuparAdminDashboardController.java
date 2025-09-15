package com.abroad.Controller;


import com.abroad.Service.EnquiryService;
import com.abroad.Service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<Map<String, Object>> getCountByCourse(@RequestParam String branchCode) {
        return enquiryService.getInquiryCountByCourseAsMap(branchCode);
    }

    @GetMapping("/count/by-stream")
    public List<Map<String, Object>> getCountByStream(@RequestParam String branchCode) {
        return enquiryService.getInquiryCountByStreamAsMap(branchCode);
    }


}
