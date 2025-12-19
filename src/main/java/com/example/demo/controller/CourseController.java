package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @GetMapping("/university/{universityId}")
    public List<Course> getCoursesByUniversity(@PathVariable Long universityId) {
        return courseService.getCoursesByUniversity(universityId);
    }

    @DeleteMapping("/{id}")
    public void deactivateCourse(@PathVariable Long id) {
        courseService.deactivateCourse(id);
    }
}
