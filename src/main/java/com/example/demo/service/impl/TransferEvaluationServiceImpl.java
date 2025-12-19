package com.example.demo.service.impl;

import com.example.demo.entity.Course;
import com.example.demo.entity.CourseContentTopic;
import com.example.demo.entity.TransferEvaluationResult;
import com.example.demo.entity.TransferRule;
import com.example.demo.repository.CourseContentTopicRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TransferEvaluationResultRepository;
import com.example.demo.repository.TransferRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferEvaluationServiceImpl {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseContentTopicRepository topicRepository;

    @Autowired
    private TransferRuleRepository ruleRepository;

    @Autowired
    private TransferEvaluationResultRepository resultRepository;

    public TransferEvaluationResult evaluateTransfer(Long sourceCourseId,
                                                     Long targetCourseId) {

        Course source = courseRepository.findById(sourceCourseId)
                .orElseThrow(() -> new RuntimeException("Source course not found"));
        Course target = courseRepository.findById(targetCourseId)
                .orElseThrow(() -> new RuntimeException("Target course not found"));

        List<CourseContentTopic> sourceTopics =
                topicRepository.findByCourseId(sourceCourseId);
        List<CourseContentTopic> targetTopics =
                topicRepository.findByCourseId(targetCourseId);

        double matchedWeight = 0;
        for (CourseContentTopic s : sourceTopics) {
            for (CourseContentTopic t : targetTopics) {
                if (s.getTopicName().equalsIgnoreCase(t.getTopicName())) {
                    matchedWeight += Math.min(
                            s.getWeightPercentage(),
                            t.getWeightPercentage()
                    );
                }
            }
        }

        double overlapPercentage = matchedWeight;

        List<TransferRule> rules =
                ruleRepository.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
                        source.getUniversity().getId(),
                        target.getUniversity().getId()
                );

        boolean eligible = false;
        for (TransferRule rule : rules) {
            int tolerance = rule.getCreditHourTolerance() == null ? 0
                    : rule.getCreditHourTolerance();
            if (overlapPercentage >= rule.getMinimumOverlapPercentage()
                    && Math.abs(source.getCreditHours() - target.getCreditHours()) <= tolerance) {
                eligible = true;
                break;
            }
        }

        TransferEvaluationResult result = new TransferEvaluationResult();
        result.setSourceCourseId(sourceCourseId);
        result.setOverlapPercentage(overlapPercentage);
        result.setIsEligibleForTransfer(eligible);
        result.setNotes(eligible ? "Eligible" : "Not Eligible");

        return resultRepository.save(result);
    }

    public TransferEvaluationResult getEvaluationById(Long id) {
        return resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found"));
    }

    public List<TransferEvaluationResult> getEvaluationsForCourse(Long courseId) {
        return resultRepository.findBySourceCourseId(courseId);
    }
}
