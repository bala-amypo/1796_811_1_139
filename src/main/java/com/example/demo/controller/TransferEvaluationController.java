package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.TransferEvaluationResult;
import com.example.demo.service.TransferEvaluationService;

@RestController
@RequestMapping("/evaluations")
public class TransferEvaluationController {

    @Autowired
    private TransferEvaluationService evaluationService;

    @PostMapping
    public TransferEvaluationResult evaluateTransfer(
            @RequestParam Long sourceCourseId,
            @RequestParam Long targetCourseId) {

        return evaluationService.evaluateTransfer(sourceCourseId, targetCourseId);
    }

    @GetMapping("/{id}")
    public TransferEvaluationResult getEvaluation(@PathVariable Long id) {
        return evaluationService.getEvaluationById(id);
    }

    @GetMapping("/course/{courseId}")
    public List<TransferEvaluationResult> getEvaluationsForCourse(
            @PathVariable Long courseId) {

        return evaluationService.getEvaluationsForCourse(courseId);
    }
}
