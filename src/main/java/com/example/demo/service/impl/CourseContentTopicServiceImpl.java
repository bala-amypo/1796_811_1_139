package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Course;
import com.example.demo.entity.CourseContentTopic;
import com.example.demo.repository.CourseContentTopicRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.service.CourseContentTopicService;

@Service
public class CourseContentTopicServiceImpl implements CourseContentTopicService {

    private CourseContentTopicRepository repo;
    private CourseRepository courseRepo;

    @Override
    public CourseContentTopic createTopic(CourseContentTopic topic) {
        if (topic.getTopicName() == null || topic.getTopicName().isBlank()) {
            throw new IllegalArgumentException("Topic name required");
        }
        if (topic.getWeightPercentage() < 0 || topic.getWeightPercentage() > 100) {
            throw new IllegalArgumentException("Weight must be 0-100");
        }

        Course course = courseRepo.findById(topic.getCourse().getId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        topic.setCourse(course);
        return repo.save(topic);
    }

    @Override
    public CourseContentTopic updateTopic(Long id, CourseContentTopic topic) {
        CourseContentTopic existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        existing.setTopicName(topic.getTopicName());
        existing.setWeightPercentage(topic.getWeightPercentage());
        return repo.save(existing);
    }

    @Override
    public List<CourseContentTopic> getTopicsForCourse(Long courseId) {
        return repo.findByCourseId(courseId);
    }

    @Override
    public CourseContentTopic getTopicById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
    }
}
    