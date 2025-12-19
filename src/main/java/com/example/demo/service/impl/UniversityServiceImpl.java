package com.example.demo.service.impl;

import com.example.demo.entity.University;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private UniversityRepository repository;

    @Override
    public University createUniversity(University u) {
        return repository.save(u);
    }

    @Override
    public University updateUniversity(Long id, University u) {
        University ex = repository.findById(id).orElseThrow();
        ex.setName(u.getName());
        return repository.save(ex);
    }

    @Override
    public University getUniversityById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public void deactivateUniversity(Long id) {
        University u = repository.findById(id).orElseThrow();
        u.setActive(false);
        repository.save(u);
    }
}
