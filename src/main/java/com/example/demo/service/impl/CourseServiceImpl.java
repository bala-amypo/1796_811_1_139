package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UniversityRepository universityRepository;

    public Course createCourse(Course course) {
        if (course.getCreditHours() <= 0) {
            throw new IllegalArgumentException("Credit hours must be greater than zero");
        }

        Long universityId = course.getUniversity().getId();
        universityRepository.findById(universityId)
                .orElseThrow(() -> new RuntimeException("University not found"));

        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course course) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        existing.setCourseName(course.getCourseName());
        existing.setCreditHours(course.getCreditHours());
        return courseRepository.save(existing);
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public void deactivateCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setActive(false);
        courseRepository.save(course);
    }

    public List<Course> getCoursesByUniversity(Long universityId) {
        return courseRepository.findByUniversityIdAndActiveTrue(universityId);
    }
}
