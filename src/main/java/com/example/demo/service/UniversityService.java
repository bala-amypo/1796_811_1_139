package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.University;

public interface UniversityService {

    University createUniversity(University university);

    University updateUniversity(Long id, University university);

    University getUniversityById(Long id);

    List<University> getAllUniversities();

    void deactivateUniversity(Long id);
}
