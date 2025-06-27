package com.abroad.Service;

import com.abroad.Entity.AbroadConsultation_Booking;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ConsultationBookingService {
    AbroadConsultation_Booking createBooking(AbroadConsultation_Booking booking, MultipartFile image, String role, String email);
    List<AbroadConsultation_Booking> getAllBookings(String role, String email);
    AbroadConsultation_Booking getBookingById(Long id, String role, String email);
    AbroadConsultation_Booking updateBooking(Long id, AbroadConsultation_Booking booking, MultipartFile image, String role, String email);
    void deleteBooking(Long id, String role, String email);
}
