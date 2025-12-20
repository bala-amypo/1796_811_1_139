package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.TransferEvaluationResult;
import com.example.demo.service.TransferEvaluationService;

@RestController
@RequestMapping("/evaluations")
public class TransferEvaluationController {

    private final TransferEvaluationService service;

    public TransferEvaluationController(TransferEvaluationService service) {
        this.service = service;
    }

    @PostMapping
    public TransferEvaluationResult evaluate(
            @RequestParam Long sourceCourseId,
            @RequestParam Long targetCourseId) {
        return service.evaluateTransfer(sourceCourseId, targetCourseId);
    }

    @GetMapping("/{id}")
    public TransferEvaluationResult getById(@PathVariable Long id) {
        return service.getEvaluationById(id);
    }

    @GetMapping("/course/{courseId}")
    public List<TransferEvaluationResult> getByCourse(@PathVariable Long courseId) {
        return service.getEvaluationsForCourse(courseId);
    }
}
