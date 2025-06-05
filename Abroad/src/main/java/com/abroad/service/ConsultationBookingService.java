package com.abroad.service;

import com.abroad.entity.Consultation_Booking;

import java.util.List;

public interface ConsultationBookingService {
    void bookConsultation(Consultation_Booking booking);
    List<Consultation_Booking> getAllBookings();

    Consultation_Booking getBookingById(Long id);

    Consultation_Booking updateBooking(Long id, Consultation_Booking booking);

    void deleteBooking(Long id);
}
