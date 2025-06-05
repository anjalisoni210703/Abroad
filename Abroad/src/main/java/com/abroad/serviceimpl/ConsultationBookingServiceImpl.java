package com.abroad.serviceimpl;

import com.abroad.entity.Consultation_Booking;
import com.abroad.entity.Continent;
import com.abroad.entity.Course;
import com.abroad.repository.ConsultationBookingRepository;
import com.abroad.repository.ContinentRepository;
import com.abroad.repository.CourseRepository;
import com.abroad.service.ConsultationBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationBookingServiceImpl  implements ConsultationBookingService {
    @Autowired
    private ConsultationBookingRepository bookingRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ContinentRepository continentRepository;

    @Override
    public void bookConsultation(Consultation_Booking booking) {
        Long courseId = booking.getCourse() != null ? booking.getCourse().getId() : null;
        Long continentId = booking.getContinent() != null ? booking.getContinent().getId() : null;

        if (courseId == null || continentId == null) {
            throw new RuntimeException("Course or Continent is missing");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Continent continent = continentRepository.findById(continentId)
                .orElseThrow(() -> new RuntimeException("Continent not found"));

        booking.setCourse(course);
        booking.setContinent(continent);

        bookingRepository.save(booking);
    }
    @Override
    public List<Consultation_Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Consultation_Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id " + id));
    }

    @Override
    public Consultation_Booking updateBooking(Long id, Consultation_Booking booking) {
        Consultation_Booking existingBooking = getBookingById(id);

        existingBooking.setFullname(booking.getFullname());
        existingBooking.setEmail(booking.getEmail());
        existingBooking.setPhoneno(booking.getPhoneno());
        existingBooking.setPincode(booking.getPincode());

        existingBooking.setCourse(null);
        existingBooking.setContinent(null);
        if (booking.getCourse() != null) existingBooking.setCourse(null);
        if (booking.getContinent() != null) existingBooking.setContinent(null);

        validateAndSetRelations(booking);
        existingBooking.setCourse(booking.getCourse());
        existingBooking.setContinent(booking.getContinent());

        return bookingRepository.save(existingBooking);
    }


    @Override
    public void deleteBooking(Long id) {
        Consultation_Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }

    private void validateAndSetRelations(Consultation_Booking booking) {
        Long courseId = booking.getCourse() != null ? booking.getCourse().getId() : null;
        Long continentId = booking.getContinent() != null ? booking.getContinent().getId() : null;

        if (courseId == null || continentId == null) {
            throw new RuntimeException("Course or Continent is missing");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Continent continent = continentRepository.findById(continentId)
                .orElseThrow(() -> new RuntimeException("Continent not found"));

        booking.setCourse(course);
        booking.setContinent(continent);
    }

}
