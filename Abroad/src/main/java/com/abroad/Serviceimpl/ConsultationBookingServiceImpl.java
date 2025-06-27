package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadConsultation_Booking;
import com.abroad.Repository.ConsultationBookingRepository;
import com.abroad.Service.ConsultationBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abroad.Service.PermissionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ConsultationBookingServiceImpl  implements ConsultationBookingService {
    @Autowired
    private ConsultationBookingRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadConsultation_Booking createBooking(AbroadConsultation_Booking booking, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create booking");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        booking.setCreatedByEmail(email);
        booking.setRole(role);
        booking.setBranchCode(branchCode);

        return repository.save(booking);
    }

    @Override
    public List<AbroadConsultation_Booking> getAllBookings(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view bookings");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public AbroadConsultation_Booking getBookingById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view booking");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Override
    public AbroadConsultation_Booking updateBooking(Long id, AbroadConsultation_Booking booking, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update booking");
        }

        AbroadConsultation_Booking existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        existing.setFullname(booking.getFullname() != null ? booking.getFullname() : existing.getFullname());
        existing.setEmail(booking.getEmail() != null ? booking.getEmail() : existing.getEmail());
        existing.setPhoneno(booking.getPhoneno() != null ? booking.getPhoneno() : existing.getPhoneno());
        existing.setPincode(booking.getPincode() != null ? booking.getPincode() : existing.getPincode());
        existing.setAbroadContinent(booking.getAbroadContinent() != null ? booking.getAbroadContinent() : existing.getAbroadContinent());
        existing.setAbroadCourse(booking.getAbroadCourse() != null ? booking.getAbroadCourse() : existing.getAbroadCourse());

        return repository.save(existing);
    }

    @Override
    public void deleteBooking(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete booking");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        repository.deleteById(id);
    }
}
