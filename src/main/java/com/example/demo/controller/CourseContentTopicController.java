package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.CourseContentTopic;
import com.example.demo.service.CourseContentTopicService;

@RestController
@RequestMapping("/topics")
public class CourseContentTopicController {

    private final CourseContentTopicService service;

    public CourseContentTopicController(CourseContentTopicService service) {
        this.service = service;
    }

    @PostMapping
    public CourseContentTopic create(@RequestBody CourseContentTopic topic) {
        return service.createTopic(topic);
    }

    @PutMapping("/{id}")
    public CourseContentTopic update(@PathVariable Long id, @RequestBody CourseContentTopic topic) {
        return service.updateTopic(id, topic);
    }

    @GetMapping("/{id}")
    public CourseContentTopic getById(@PathVariable Long id) {
        return service.getTopicById(id);
    }

    @GetMapping("/course/{courseId}")
    public List<CourseContentTopic> getByCourse(@PathVariable Long courseId) {
        return service.getTopicsForCourse(courseId);
    }
}
