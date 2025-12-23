package com.example.demo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Course tgt = courseRepo.findById(targetCourseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!src.isActive() || !tgt.isActive()) {
            throw new IllegalArgumentException("Course must be active");
        }

        List<CourseContentTopic> srcTopics = topicRepo.findByCourseId(sourceCourseId);
        List<CourseContentTopic> tgtTopics = topicRepo.findByCourseId(targetCourseId);

        Map<String, Double> srcMap = new HashMap<>();
        for (CourseContentTopic t : srcTopics) {
            srcMap.put(t.getTopicName().toLowerCase(), t.getWeightPercentage());
        }

        double overlap = 0.0;
        for (CourseContentTopic t : tgtTopics) {
            String key = t.getTopicName().toLowerCase();
            if (srcMap.containsKey(key)) {
                overlap += Math.min(srcMap.get(key), t.getWeightPercentage());
            }
        }

        TransferEvaluationResult res = new TransferEvaluationResult();
        res.setSourceCourseId(sourceCourseId);
        res.setTargetCourseId(targetCourseId);
        res.setOverlapPercentage(overlap);

        List<TransferRule> rules =
                ruleRepo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
                        src.getUniversity().getId(),
                        tgt.getUniversity().getId());

        boolean eligible = false;
        String notes = "No active transfer rule";

        for (TransferRule r : rules) {
            boolean overlapOk = overlap >= r.getMinimumOverlapPercentage();
            boolean creditOk = Math.abs(src.getCreditHours() - tgt.getCreditHours())
                    <= (r.getCreditHourTolerance() == null ? 0 : r.getCreditHourTolerance());

            if (overlapOk && creditOk) {
                eligible = true;
                notes = "Transfer eligible";
                break;
            }
        }

        if (!eligible && !rules.isEmpty()) {
            notes = "No active rule satisfied";
        }

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
    public List<TransferEvaluationResult> getEvaluationsForCourse(Long sourceCourseId) {
        return resultRepo.findBySourceCourseId(sourceCourseId);
    }
}
