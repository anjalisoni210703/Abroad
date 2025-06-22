package com.abroad.service;

import com.abroad.entity.Address;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AddressService {
    Address createAddress(Address address, MultipartFile image, String role, String email);
    List<Address> getAllAddresses(String role, String email);
    Address getAddressById(Long id, String role, String email);
    Address updateAddress(Long id, Address address, MultipartFile image, String role, String email);
    void deleteAddress(Long id, String role, String email);
}
