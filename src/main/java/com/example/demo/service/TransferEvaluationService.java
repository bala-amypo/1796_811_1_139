package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.TransferEvaluationResult;

public interface TransferEvaluationService {

    TransferEvaluationResult evaluateTransfer(Long sourceCourseId, Long targetCourseId);

    TransferEvaluationResult getEvaluationById(Long id);

    List<TransferEvaluationResult> getEvaluationsForCourse(Long courseId);
}
