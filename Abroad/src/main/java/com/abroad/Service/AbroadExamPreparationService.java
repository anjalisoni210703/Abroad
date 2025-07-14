package com.abroad.Service;

import com.abroad.Entity.AbroadExamPreparation;

import java.util.List;

public interface AbroadExamPreparationService {
    AbroadExamPreparation createExam(AbroadExamPreparation exam, String role, String email);
    List<AbroadExamPreparation> getAllExams();
    AbroadExamPreparation getExamById(Long id, String role, String email);
    AbroadExamPreparation updateExam(Long id, AbroadExamPreparation exam, String role, String email);
    void deleteExam(Long id, String role, String email);
}
