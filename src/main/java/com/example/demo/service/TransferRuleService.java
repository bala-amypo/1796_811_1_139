package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.TransferRule;

public interface TransferRuleService {

    TransferRule createRule(TransferRule rule);

    TransferRule updateRule(Long id, TransferRule rule);

    TransferRule getRuleById(Long id);

    List<TransferRule> getRulesForUniversities(Long sourceUniversityId, Long targetUniversityId);

    void deactivateRule(Long id);
}
