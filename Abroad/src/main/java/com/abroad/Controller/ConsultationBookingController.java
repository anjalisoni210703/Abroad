package com.abroad.Controller;

import com.abroad.Entity.AbroadConsultation_Booking;
import com.abroad.Service.ConsultationBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class ConsultationBookingController {
    @Autowired
    private ConsultationBookingService service;

    @PostMapping("/createConsultationBooking")
    public ResponseEntity<AbroadConsultation_Booking> createBooking(@RequestPart("booking") String bookingJson,
                                                                    @RequestParam(value = "image", required = false) MultipartFile image,
                                                                    @RequestParam String role,
                                                                    @RequestParam String email) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadConsultation_Booking booking = mapper.readValue(bookingJson, AbroadConsultation_Booking.class);
        return ResponseEntity.ok(service.createBooking(booking, image, role, email));
    }

    @PutMapping("/updateConsultationBooking/{id}")
    public ResponseEntity<AbroadConsultation_Booking> updateBooking(@PathVariable Long id,
                                                                    @RequestPart("booking") String bookingJson,
                                                                    @RequestParam(value = "image", required = false) MultipartFile image,
                                                                    @RequestParam String role,
                                                                    @RequestParam String email) throws JsonProcessingException {
        AbroadConsultation_Booking booking = new ObjectMapper().readValue(bookingJson, AbroadConsultation_Booking.class);
        return ResponseEntity.ok(service.updateBooking(id, booking, image, role, email));
    }

    @GetMapping("/getAllConsultationBookings")
    public ResponseEntity<List<AbroadConsultation_Booking>> getAllBookings(@RequestParam String role,
                                                                           @RequestParam String email) {
        return ResponseEntity.ok(service.getAllBookings(role, email));
    }

    @GetMapping("/getConsultationBookingById/{id}")
    public ResponseEntity<AbroadConsultation_Booking> getBookingById(@PathVariable Long id,
                                                                     @RequestParam String role,
                                                                     @RequestParam String email) {
        return ResponseEntity.ok(service.getBookingById(id, role, email));
    }

    @DeleteMapping("/deleteConsultationBooking/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id,
                                                @RequestParam String role,
                                                @RequestParam String email) {
        service.deleteBooking(id, role, email);
        return ResponseEntity.ok("Consultation Booking deleted successfully");
    }

}
