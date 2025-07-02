package com.abroad.Serviceimpl;

import com.abroad.DTO.AuthRequest;
import com.abroad.DTO.LoginResponse;
import com.abroad.Entity.AbroadContinent;
import com.abroad.Entity.AbroadCourse;
import com.abroad.Entity.AbroadUser;
import com.abroad.JWT.JwtService;
import com.abroad.Repository.AbroadUserRepository;
import com.abroad.Repository.ContinentRepository;
import com.abroad.Repository.CourseRepository;
import com.abroad.Service.AbroadUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AbroadUserServiceImpl implements AbroadUserService {

    @Autowired
    private AbroadUserRepository userRepository;

    @Autowired
    private ContinentRepository continentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    @Override
    public AbroadUser createUser(AbroadUser user) {
        // Validate and set continent
        AbroadContinent continent = continentRepository.findByContinentnameIgnoreCase(user.getContinent())
                .orElseThrow(() -> new RuntimeException("Continent not found: " + user.getContinent()));
        user.setAbroadContinent(continent);

        // Validate and set course
        AbroadCourse course = courseRepository.findByCourseNameIgnoreCase(user.getCourse())
                .orElseThrow(() -> new RuntimeException("Course not found: " + user.getCourse()));
        user.setAbroadCourse(course);

        // Permissions (optional default logic)
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCansPost(true);
        user.setCansGet(true);
        user.setCansPut(false);
        user.setCansDelete(false);

        return userRepository.save(user);
    }


    @Override
    public AbroadUser updateUser(Long id, AbroadUser updatedUser) {
        AbroadUser existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setEmail(updatedUser.getEmail() != null ? updatedUser.getEmail() : existingUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword() != null ? updatedUser.getPassword() : existingUser.getPassword());
        existingUser.setFirstName(updatedUser.getFirstName() != null ? updatedUser.getFirstName() : existingUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName() != null ? updatedUser.getLastName() : existingUser.getLastName());

        // Update continent if provided
        if (updatedUser.getContinent() != null) {
            AbroadContinent continent = continentRepository.findByContinentnameIgnoreCase(updatedUser.getContinent())
                    .orElseThrow(() -> new RuntimeException("Continent not found: " + updatedUser.getContinent()));
            existingUser.setAbroadContinent(continent);
        }

        // Update course if provided
        if (updatedUser.getCourse() != null) {
            AbroadCourse course = courseRepository.findByCourseNameIgnoreCase(updatedUser.getCourse())
                    .orElseThrow(() -> new RuntimeException("Course not found: " + updatedUser.getCourse()));
            existingUser.setAbroadCourse(course);
        }

        return userRepository.save(existingUser);
    }



    @Override
    public AbroadUser getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public List<AbroadUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }



    @Override
    public LoginResponse login(AuthRequest loginRequest) {
        AbroadUser user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());
        data.put("firstName", user.getFirstName());
        data.put("continent", user.getContinent());
        data.put("course", user.getCourse());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setData(data);
        response.setMassage("successful");

        return response;
    }

    @Override
    public Map<String, Object> getPermissionsByEmail(String email) {
        AbroadUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("AbroadUser not found with email: " + email));

        Map<String, Object> permissions = new HashMap<>();
        permissions.put("candGet", user.isCansGet());
        permissions.put("candPut", user.isCansPut());
        permissions.put("candPost", user.isCansPost());
        permissions.put("candDelete", user.isCansDelete());

        return permissions;
    }

}