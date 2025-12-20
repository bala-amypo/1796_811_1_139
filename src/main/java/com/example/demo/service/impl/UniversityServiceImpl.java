package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.University;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.UniversityService;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private UniversityRepository repository;

    @Override
    public University createUniversity(University university) {
        if (university == null || university.getName() == null || university.getName().isBlank()) {
            throw new IllegalArgumentException("Name required");
        }

        repository.findByName(university.getName())
                .ifPresent(u -> { throw new IllegalArgumentException("University already exists"); });

        return repository.save(university);
    }

    @Override
    public University updateUniversity(Long id, University university) {
        University existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));

        if (university.getName() != null) {
            existing.setName(university.getName());
        }

        return repository.save(existing);
    }

    @Override
    public University getUniversityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));
    }

    @Override
    public List<University> getAllUniversities() {
        return repository.findAll();
    }

    @Override
    public void deactivateUniversity(Long id) {
        University u = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));
        u.setActive(false);
        repository.save(u);
    }
}
