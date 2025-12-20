package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.TransferRule;
import com.example.demo.service.TransferRuleService;

@RestController
@RequestMapping("/rules")
public class TransferRuleController {

    private final TransferRuleService service;

    public TransferRuleController(TransferRuleService service) {
        this.service = service;
    }

    @PostMapping
    public TransferRule create(@RequestBody TransferRule rule) {
        return service.createRule(rule);
    }

    @PutMapping("/{id}")
    public TransferRule update(@PathVariable Long id, @RequestBody TransferRule rule) {
        return service.updateRule(id, rule);
    }

    @GetMapping("/{id}")
    public TransferRule getById(@PathVariable Long id) {
        return service.getRuleById(id);
    }

    @GetMapping("/between")
    public List<TransferRule> getRules(
            @RequestParam Long sourceUniversityId,
            @RequestParam Long targetUniversityId) {
        return service.getRulesForUniversities(sourceUniversityId, targetUniversityId);
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable Long id) {
        service.deactivateRule(id);
    }
}
