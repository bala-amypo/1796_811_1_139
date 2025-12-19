package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class TransferRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double minimumOverlapPercentage;
    private Integer creditHourTolerance;
    private boolean active = true;

    @ManyToOne
    private University sourceUniversity;

    @ManyToOne
    private University targetUniversity;

    public TransferRule() {
    }

    // getters & setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMinimumOverlapPercentage() {
        return minimumOverlapPercentage;
    }

    public void setMinimumOverlapPercentage(Double minimumOverlapPercentage) {
        this.minimumOverlapPercentage = minimumOverlapPercentage;
    }

    public Integer getCreditHourTolerance() {
        return creditHourTolerance;
    }

    public void setCreditHourTolerance(Integer creditHourTolerance) {
        this.creditHourTolerance = creditHourTolerance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public University getSourceUniversity() {
        return sourceUniversity;
    }

    public void setSourceUniversity(University sourceUniversity) {
        this.sourceUniversity = sourceUniversity;
    }

    public University getTargetUniversity() {
        return targetUniversity;
    }

    public void setTargetUniversity(University targetUniversity) {
        this.targetUniversity = targetUniversity;
    }
}
