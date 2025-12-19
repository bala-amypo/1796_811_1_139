package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TransferEvaluationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isEligibleForTransfer;
    private Double overlapPercentage;
    private String notes;
    private Long sourceCourseId;

    public TransferEvaluationResult() {
    }

    // getters & setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsEligibleForTransfer() {
        return isEligibleForTransfer;
    }

    public void setIsEligibleForTransfer(Boolean isEligibleForTransfer) {
        this.isEligibleForTransfer = isEligibleForTransfer;
    }

    public Double getOverlapPercentage() {
        return overlapPercentage;
    }

    public void setOverlapPercentage(Double overlapPercentage) {
        this.overlapPercentage = overlapPercentage;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getSourceCourseId() {
        return sourceCourseId;
    }

    public void setSourceCourseId(Long sourceCourseId) {
        this.sourceCourseId = sourceCourseId;
    }
}
