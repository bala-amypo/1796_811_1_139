package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.CourseContentTopic;
import com.example.demo.service.CourseContentTopicService;

@RestController
@RequestMapping("/topics")
public class CourseContentTopicController {

    @Autowired
    private CourseContentTopicService topicService;

    @PostMapping
    public CourseContentTopic createTopic(@RequestBody CourseContentTopic topic) {
        return topicService.createTopic(topic);
    }

    @PutMapping("/{id}")
    public CourseContentTopic updateTopic(@PathVariable Long id,
                                          @RequestBody CourseContentTopic topic) {
        return topicService.updateTopic(id, topic);
    }

    @GetMapping("/course/{courseId}")
    public List<CourseContentTopic> getTopicsForCourse(@PathVariable Long courseId) {
        return topicService.getTopicsForCourse(courseId);
    }

    @GetMapping("/{id}")
    public CourseContentTopic getTopic(@PathVariable Long id) {
        return topicService.getTopicById(id);
    }
}
