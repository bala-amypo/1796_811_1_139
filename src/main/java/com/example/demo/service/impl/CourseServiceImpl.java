package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Course;
import com.example.demo.entity.University;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository repo;
    private UniversityRepository univRepo;

    @Override
    public Course createCourse(Course course) {
        if (course.getCreditHours() <= 0) {
            throw new IllegalArgumentException("Credit hours must be > 0");
        }

        Long universityId = course.getUniversity().getId();
        University u = univRepo.findById(universityId)
                .orElseThrow(() -> new RuntimeException("University not found"));

        if (repo.findByUniversityIdAndCourseCode(universityId, course.getCourseCode()).isPresent()) {
            throw new IllegalArgumentException("Duplicate course code");
        }

        course.setUniversity(u);
        return repo.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        Course existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        existing.setCourseName(course.getCourseName());
        return repo.save(existing);
    }

    @Override
    public Course getCourseById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public void deactivateCourse(Long id) {
        Course c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        c.setActive(false);
        repo.save(c);
    }

    @Override
    public List<Course> getCoursesByUniversity(Long universityId) {
        return repo.findByUniversityIdAndActiveTrue(universityId);
    }
}
