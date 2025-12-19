package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Course;

public interface CourseService {

    Course createCourse(Course course);

    Course updateCourse(Long id, Course course);

    Course getCourseById(Long id);

    void deactivateCourse(Long id);

    List<Course> getCoursesByUniversity(Long universityId);
}
