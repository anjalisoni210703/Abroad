package com.abroad.serviceimpl;

import com.abroad.entity.Course;
import com.abroad.repository.CourseRepository;
import com.abroad.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + id));
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        Course existingCourse = getCourseById(id);

        existingCourse.setCourseName(course.getCourseName());
        existingCourse.setPrice(course.getPrice());
        existingCourse.setDiscountPrice(course.getDiscountPrice());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setDate(course.getDate());
        existingCourse.setValidity(course.getValidity());
        existingCourse.setCategoryName(course.getCategoryName());
        existingCourse.setThumbnail(course.getThumbnail());

        return courseRepository.save(existingCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }

}
