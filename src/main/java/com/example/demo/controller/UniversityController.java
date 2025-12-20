package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.University;
import com.example.demo.service.UniversityService;

@RestController
@RequestMapping("/universities")
public class UniversityController {

    private final UniversityService service;

    public UniversityController(UniversityService service) {
        this.service = service;
    }

    @PostMapping
    public University create(@RequestBody University university) {
        return service.createUniversity(university);
    }

    @PutMapping("/{id}")
    public University update(@PathVariable Long id, @RequestBody University university) {
        return service.updateUniversity(id, university);
    }

    @GetMapping("/{id}")
    public University getById(@PathVariable Long id) {
        return service.getUniversityById(id);
    }

    @GetMapping
    public List<University> getAll() {
        return service.getAllUniversities();
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable Long id) {
        service.deactivateUniversity(id);
    }
}
