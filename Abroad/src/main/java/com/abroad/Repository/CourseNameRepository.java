package com.abroad.Repository;

import com.abroad.Entity.CourseName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseNameRepository extends JpaRepository<CourseName,Long> {
}
