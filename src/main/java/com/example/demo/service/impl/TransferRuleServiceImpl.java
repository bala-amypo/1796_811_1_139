package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.TransferRule;
import com.example.demo.repository.TransferRuleRepository;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.TransferRuleService;

@Service
public class TransferRuleServiceImpl implements TransferRuleService {

    private TransferRuleRepository repo;
    private UniversityRepository univRepo;

    @Override
    public TransferRule createRule(TransferRule rule) {
        if (rule.getMinimumOverlapPercentage() < 0 || rule.getMinimumOverlapPercentage() > 100) {
            throw new IllegalArgumentException("Overlap must be 0-100");
        }

        univRepo.findById(rule.getSourceUniversity().getId())
                .orElseThrow(() -> new RuntimeException("University not found"));
        univRepo.findById(rule.getTargetUniversity().getId())
                .orElseThrow(() -> new RuntimeException("University not found"));

        return repo.save(rule);
    }

    @Override
    public TransferRule updateRule(Long id, TransferRule rule) {
        TransferRule existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
        return repo.save(existing);
    }

    @Override
    public void deactivateRule(Long id) {
        TransferRule r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
        r.setActive(false);
        repo.save(r);
    }

    @Override
    public TransferRule getRuleById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
    }

    @Override
    public List<TransferRule> getRulesForUniversities(Long sourceUniversityId, Long targetUniversityId) {
        return repo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
                sourceUniversityId, targetUniversityId);
    }
}
