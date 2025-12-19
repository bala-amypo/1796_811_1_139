package com.example.demo.service.impl;

import com.example.demo.entity.CourseContentTopic;
import com.example.demo.repository.CourseContentTopicRepository;
import com.example.demo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseContentTopicServiceImpl {

    @Autowired
    private CourseContentTopicRepository repository;

    @Autowired
    private CourseRepository courseRepository;

    public CourseContentTopic createTopic(CourseContentTopic topic) {
        if (topic.getTopicName() == null || topic.getTopicName().isBlank()) {
            throw new IllegalArgumentException("Topic name required");
        }
        if (topic.getWeightPercentage() < 0 || topic.getWeightPercentage() > 100) {
            throw new IllegalArgumentException("Weight must be between 0 and 100");
        }

        courseRepository.findById(topic.getCourse().getId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return repository.save(topic);
    }

    public CourseContentTopic updateTopic(Long id, CourseContentTopic topic) {
        CourseContentTopic existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        existing.setTopicName(topic.getTopicName());
        existing.setWeightPercentage(topic.getWeightPercentage());
        return repository.save(existing);
    }

    public CourseContentTopic getTopicById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
    }

    public List<CourseContentTopic> getTopicsForCourse(Long courseId) {
        return repository.findByCourseId(courseId);
    }
}
