package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.University;
import com.example.demo.service.UniversityService;

@RestController
@RequestMapping("/universities")
public class UniversityController {

    @Autowired
    private UniversityService universityService;

    @PostMapping
    public University createUniversity(@RequestBody University university) {
        return universityService.createUniversity(university);
    }

    @PutMapping("/{id}")
    public University updateUniversity(@PathVariable Long id,
                                       @RequestBody University university) {
        return universityService.updateUniversity(id, university);
    }

    @GetMapping("/{id}")
    public University getUniversity(@PathVariable Long id) {
        return universityService.getUniversityById(id);
    }

    @GetMapping
    public List<University> getAllUniversities() {
        return universityService.getAllUniversities();
    }

    @PutMapping("/{id}/deactivate")
    public void deactivateUniversity(@PathVariable Long id) {
        universityService.deactivateUniversity(id);
    }
}
