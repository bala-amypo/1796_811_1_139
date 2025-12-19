package com.example.demo.service.impl;

import com.example.demo.entity.TransferRule;
import com.example.demo.repository.TransferRuleRepository;
import com.example.demo.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferRuleServiceImpl {

    @Autowired
    private TransferRuleRepository repository;

    @Autowired
    private UniversityRepository universityRepository;

    public TransferRule createRule(TransferRule rule) {
        if (rule.getMinimumOverlapPercentage() < 0 ||
            rule.getMinimumOverlapPercentage() > 100) {
            throw new IllegalArgumentException("Invalid overlap percentage");
        }

        universityRepository.findById(rule.getSourceUniversity().getId())
                .orElseThrow(() -> new RuntimeException("Source university not found"));
        universityRepository.findById(rule.getTargetUniversity().getId())
                .orElseThrow(() -> new RuntimeException("Target university not found"));

        return repository.save(rule);
    }

    public TransferRule updateRule(Long id, TransferRule rule) {
        TransferRule existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
        existing.setMinimumOverlapPercentage(rule.getMinimumOverlapPercentage());
        existing.setCreditHourTolerance(rule.getCreditHourTolerance());
        return repository.save(existing);
    }

    public TransferRule getRuleById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
    }

    public void deactivateRule(Long id) {
        TransferRule rule = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
        rule.setActive(false);
        repository.save(rule);
    }

    public List<TransferRule> getRulesForUniversities(Long sourceId, Long targetId) {
        return repository
                .findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(sourceId, targetId);
    }
}
