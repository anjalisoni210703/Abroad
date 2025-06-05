package com.abroad.controller;

import com.abroad.entity.Consultation_Booking;
import com.abroad.service.ConsultationBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConsultationBookingController {
    @Autowired
    private ConsultationBookingService bookingService;

    @PostMapping("/bookConsultation")
    public ResponseEntity<String> bookConsultation(@RequestBody Consultation_Booking booking) {
        bookingService.bookConsultation(booking);
        return ResponseEntity.ok("Consultation booked successfully.");
    }
    @GetMapping("getAllBookings")
    public ResponseEntity<List<Consultation_Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/getBookingById/{id}")
    public ResponseEntity<Consultation_Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @PutMapping("/updateBooking/{id}")
    public ResponseEntity<Consultation_Booking> updateBooking(@PathVariable Long id,
                                                              @RequestBody Consultation_Booking booking) {
        Consultation_Booking updatedBooking = bookingService.updateBooking(id, booking);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/deleteBooking/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("Booking deleted successfully.");
    }

}
