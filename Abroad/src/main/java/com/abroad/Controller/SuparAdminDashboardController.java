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

    /// BY ALL Count ->>>>>>
    @GetMapping("/count/by-course")
    public List<Map<String, Object>> getCountByCourse(@RequestParam(required = false) String branchCode) {
        return enquiryService.getInquiryCountByCourseAsMap(branchCode);
    }

    @GetMapping("/count/by-stream")
    public List<Map<String, Object>> getCountByStream(@RequestParam(required = false) String branchCode) {
        return enquiryService.getInquiryCountByStreamAsMap(branchCode);
    }

    @GetMapping("/count/by-conduct")
    public ResponseEntity<List<Map<String, Object>>> getInquiryCountByConductBy(
            @RequestParam(required = false) String branchCode) {
        List<Map<String, Object>> response = enquiryService.getInquiryCountByConductByAsMap(branchCode);
        return ResponseEntity.ok(response);
    }

    /// BY MONTH ""->>>>>> ******
    @GetMapping("/stream/by-month")
    public ResponseEntity<List<Map<String, Object>>> getInquiryCountByStream(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer endYear,
            @RequestParam(required = false) String branchCode) {

        List<Map<String, Object>> response;

        if (month != null) {
            // existing month logic
            response = enquiryService.getInquiryCountByStreamForMonth(month, branchCode);
        } else if (startYear != null && endYear != null) {
            // new year range logic
            response = enquiryService.getInquiryCountByStreamForYearRange(startYear, endYear, branchCode);
        } else {
            throw new IllegalArgumentException("Please provide either month or year range");
        }

        return ResponseEntity.ok(response);
    }


    /// Count by course for a specific month ************
    @GetMapping("/course/by-month")
    public ResponseEntity<List<Map<String, Object>>> getInquiryCountByCourse(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer endYear,
            @RequestParam(required = false) String branchCode) {

        List<Map<String, Object>> response;

        if (month != null) {
            // month filter
            response = enquiryService.getInquiryCountByCourseForMonth(month, branchCode);
        } else if (startYear != null && endYear != null) {
            // year range filter
            response = enquiryService.getInquiryCountByCourseForYearRange(startYear, endYear, branchCode);
        } else {
            throw new IllegalArgumentException("Please provide either month or year range");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/conduct/by-month")
    public ResponseEntity<List<Map<String, Object>>> getInquiryCountByConductByForMonth(
            @RequestParam int month,
            @RequestParam(required = false) String branchCode) {
        List<Map<String, Object>> response = enquiryService.getInquiryCountByConductByForMonth(month, branchCode);
        return ResponseEntity.ok(response);
    }

    /// //
    @GetMapping("/TotalCount")
    public ResponseEntity<Map<String, Long>> getAllEnquiryCounts(
            @RequestParam(required = false) String branchCode) {
        Map<String, Long> response = enquiryService.getAllEnquiryCounts(branchCode);
        return ResponseEntity.ok(response);
    }
    /// /

    @GetMapping("/status-count")
    public ResponseEntity<Map<String, Long>> getAllStatusWiseCount(
            @RequestParam(required = false) String branchCode) {
        Map<String, Long> response = enquiryService.getAllStatusWiseCount(branchCode);
        return ResponseEntity.ok(response);
    }



}
