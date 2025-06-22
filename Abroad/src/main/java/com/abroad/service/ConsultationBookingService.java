package com.abroad.service;

import com.abroad.entity.Consultation_Booking;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ConsultationBookingService {
    Consultation_Booking createBooking(Consultation_Booking booking, MultipartFile image, String role, String email);
    List<Consultation_Booking> getAllBookings(String role, String email);
    Consultation_Booking getBookingById(Long id, String role, String email);
    Consultation_Booking updateBooking(Long id, Consultation_Booking booking, MultipartFile image, String role, String email);
    void deleteBooking(Long id, String role, String email);
}
