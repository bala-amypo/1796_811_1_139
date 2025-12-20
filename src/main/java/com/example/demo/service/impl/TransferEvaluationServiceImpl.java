package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CourseRepository courseRepo;
    @Autowired
    private CourseContentTopicRepository topicRepo;
    @Autowired
    private TransferRuleRepository ruleRepo;
    @Autowired
    private TransferEvaluationResultRepository resultRepo;

    @Override
    public TransferEvaluationResult evaluateTransfer(Long sourceCourseId, Long targetCourseId) {

        Course src = courseRepo.findById(sourceCourseId)
                .orElseThrow(() -> new RuntimeException("Source course not found"));
        Course tgt = courseRepo.findById(targetCourseId)
                .orElseThrow(() -> new RuntimeException("Target course not found"));

        if (!src.isActive() || !tgt.isActive()) {
            throw new IllegalArgumentException("Courses must be active");
        }

        List<CourseContentTopic> srcTopics = topicRepo.findByCourseId(sourceCourseId);
        List<CourseContentTopic> tgtTopics = topicRepo.findByCourseId(targetCourseId);

        double overlap = 0;
        for (CourseContentTopic s : srcTopics) {
            for (CourseContentTopic t : tgtTopics) {
                if (s.getTopicName().equalsIgnoreCase(t.getTopicName())) {
                    overlap += Math.min(s.getWeightPercentage(), t.getWeightPercentage());
                }
            }
        }

        if (srcTopics.isEmpty()) {
            overlap = 0;
        }

        List<TransferRule> rules = ruleRepo
                .findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
                        src.getUniversity().getId(),
                        tgt.getUniversity().getId());

        boolean eligible = false;
        String notes = "No active rule satisfied";

        for (TransferRule r : rules) {
            boolean creditOk = Math.abs(src.getCreditHours() - tgt.getCreditHours())
                    <= r.getCreditHourTolerance();

            if (overlap >= r.getMinimumOverlapPercentage() && creditOk) {
                eligible = true;
                notes = "Eligible based on transfer rule";
                break;
            }
        }

        if (rules.isEmpty()) {
            notes = "No active transfer rule";
        }

        TransferEvaluationResult res = new TransferEvaluationResult();
        res.setSourceCourseId(sourceCourseId);
        res.setOverlapPercentage(overlap);
        res.setIsEligibleForTransfer(eligible);
        res.setNotes(notes);

        return resultRepo.save(res);
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
