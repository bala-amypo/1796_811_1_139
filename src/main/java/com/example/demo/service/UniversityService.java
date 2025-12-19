package com.example.demo.service;

import com.example.demo.entity.University;

public interface UniversityService {

    University createUniversity(University u);

    University updateUniversity(Long id, University u);

    University getUniversityById(Long id);

    void deactivateUniversity(Long id);
}
