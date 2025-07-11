package com.abroad.Service;

import com.abroad.Entity.AbroadExam;

import java.util.List;

public interface AbroadExamService {
    AbroadExam createExam(AbroadExam exam, String role, String email);
    List<AbroadExam> getAllExams();
    AbroadExam getExamById(Long id, String role, String email);
    AbroadExam updateExam(Long id, AbroadExam exam, String role, String email);
    void deleteExam(Long id, String role, String email);
}
