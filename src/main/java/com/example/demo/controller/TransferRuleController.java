package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.TransferRule;
import com.example.demo.service.TransferRuleService;

@RestController
@RequestMapping("/rules")
public class TransferRuleController {

    @Autowired
    private TransferRuleService ruleService;

    @PostMapping
    public TransferRule createRule(@RequestBody TransferRule rule) {
        return ruleService.createRule(rule);
    }

    @PutMapping("/{id}")
    public TransferRule updateRule(@PathVariable Long id, @RequestBody TransferRule rule) {
        return ruleService.updateRule(id, rule);
    }

    @GetMapping("/{id}")
    public TransferRule getRule(@PathVariable Long id) {
        return ruleService.getRuleById(id);
    }

    @GetMapping("/universities")
    public List<TransferRule> getRules(@RequestParam Long sourceUniversityId, @RequestParam Long targetUniversityId) {
        return ruleService.getRulesForUniversities(sourceUniversityId, targetUniversityId);
    }

    @DeleteMapping("/{id}")
    public void deactivateRule(@PathVariable Long id) {
        ruleService.deactivateRule(id);
    }
}
