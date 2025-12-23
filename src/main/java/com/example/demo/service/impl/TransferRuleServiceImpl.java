package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TransferRule;
import com.example.demo.entity.University;
import com.example.demo.repository.TransferRuleRepository;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.TransferRuleService;

@Service
public class TransferRuleServiceImpl implements TransferRuleService {

    @Autowired
    private TransferRuleRepository repo;
    @Autowired
    private UniversityRepository univRepo;

    @Override
    public TransferRule createRule(TransferRule rule) {
        if (rule.getMinimumOverlapPercentage() == null ||
                rule.getMinimumOverlapPercentage() < 0 ||
                rule.getMinimumOverlapPercentage() > 100) {
            throw new IllegalArgumentException("Overlap must be 0-100");
        }

        if (rule.getCreditHourTolerance() != null && rule.getCreditHourTolerance() < 0) {
            throw new IllegalArgumentException("Tolerance must be >= 0");
        }

        University s = univRepo.findById(rule.getSourceUniversity().getId())
                .orElseThrow(() -> new RuntimeException("University not found"));
        University t = univRepo.findById(rule.getTargetUniversity().getId())
                .orElseThrow(() -> new RuntimeException("University not found"));

        rule.setSourceUniversity(s);
        rule.setTargetUniversity(t);
        return repo.save(rule);
    }

    @Override
    public TransferRule updateRule(Long id, TransferRule rule) {
        TransferRule existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
        return repo.save(existing);
    }

    @Override
    public TransferRule getRuleById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
    }

    @Override
    public List<TransferRule> getRulesForUniversities(Long sId, Long tId) {
        return repo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(sId, tId);
    }

    @Override
    public void deactivateRule(Long id) {
        TransferRule r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
        r.setActive(false);
        repo.save(r);
    }
}
