package com.example.demo.service.impl;

import com.example.demo.entity.University;
import com.example.demo.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityServiceImpl {

    @Autowired
    private UniversityRepository repository;

    public University createUniversity(University university) {
        if (university.getName() == null || university.getName().isBlank()) {
            throw new IllegalArgumentException("University name required");
        }
        return repository.save(university);
    }

    public University updateUniversity(Long id, University university) {
        University existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));
        existing.setName(university.getName());
        return repository.save(existing);
    }

    public University getUniversityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));
    }

    public void deactivateUniversity(Long id) {
        University university = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));
        university.setActive(false);
        repository.save(university);
    }
}
