package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.entity.University;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UniversityServiceImpl {

    @Autowired
    private UniversityRepository repository;

    public University createUniversity(University u) {
        return repository.save(u);
    }

    public University updateUniversity(Long id, University u) {
        University ex = repository.findById(id).orElseThrow();
        ex.setName(u.getName());
        return repository.save(ex);
    }

    public University getUniversityById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public void deactivateUniversity(Long id) {
        University u = repository.findById(id).orElseThrow();
        u.setActive(false);
        repository.save(u);
    }
}
