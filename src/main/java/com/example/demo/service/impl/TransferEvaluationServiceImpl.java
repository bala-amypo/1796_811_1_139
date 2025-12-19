package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Course;
import com.example.demo.entity.CourseContentTopic;
import com.example.demo.entity.TransferEvaluationResult;
import com.example.demo.entity.TransferRule;
import com.example.demo.repository.CourseContentTopicRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TransferEvaluationResultRepository;
import com.example.demo.repository.TransferRuleRepository;
import com.example.demo.service.TransferEvaluationService;

@Service
public class TransferEvaluationServiceImpl implements TransferEvaluationService {

    private CourseRepository courseRepo;
    private CourseContentTopicRepository topicRepo;
    private TransferRuleRepository ruleRepo;
    private TransferEvaluationResultRepository resultRepo;

    @Override
    public TransferEvaluationResult evaluateTransfer(Long sourceCourseId, Long targetCourseId) {

        Course source = courseRepo.findById(sourceCourseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Course target = courseRepo.findById(targetCourseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        double overlap = 0;
        List<CourseContentTopic> sTopics = topicRepo.findByCourseId(sourceCourseId);
        List<CourseContentTopic> tTopics = topicRepo.findByCourseId(targetCourseId);

        for (CourseContentTopic s : sTopics) {
            for (CourseContentTopic t : tTopics) {
                if (s.getTopicName().equalsIgnoreCase(t.getTopicName())) {
                    overlap += Math.min(s.getWeightPercentage(), t.getWeightPercentage());
                }
            }
        }

        boolean eligible = false;
        for (TransferRule r : ruleRepo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
                source.getUniversity().getId(), target.getUniversity().getId())) {

            int tolerance = r.getCreditHourTolerance() == null ? 0 : r.getCreditHourTolerance();
            if (overlap >= r.getMinimumOverlapPercentage()
                    && Math.abs(source.getCreditHours() - target.getCreditHours()) <= tolerance) {
                eligible = true;
                break;
            }
        }

        TransferEvaluationResult result = new TransferEvaluationResult();
        result.setSourceCourse(source);
        result.setTargetCourse(target);
        result.setOverlapPercentage(overlap);
        result.setIsEligibleForTransfer(eligible);
        result.setNotes(eligible ? "Transfer eligible" : "No active transfer rule");

        return resultRepo.save(result);
    }

    @Override
    public TransferEvaluationResult getEvaluationById(Long id) {
        return resultRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found"));
    }

    @Override
    public List<TransferEvaluationResult> getEvaluationsForCourse(Long courseId) {
        return resultRepo.findBySourceCourseId(courseId);
    }
}
