package com.abroad.Service;

import com.abroad.Entity.AbroadExam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AbroadExamService {
    AbroadExam addExam(String role, String email, AbroadExam exam);

    AbroadExam getById(String role, String email, Long id);

    List<AbroadExam> getAll(String role, String email);

    AbroadExam updateExam(Long id, String role, String email, AbroadExam uexam);

    Void deleteExam(Long id, String role, String email);
}
