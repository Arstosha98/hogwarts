package com.example.hogwarts.service;

import com.example.hogwarts.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    
    Faculty create(Faculty faculty);
    
    Faculty update(Long id, Faculty faculty);
    
    Faculty getById(Long id);
    
    void delete(Long id);
    Collection<Faculty> getAll();
    Collection<Faculty> getByColor(String color);
    Collection<Faculty> filteredByColorOrName(String color, String name);
    Faculty getByStudentId(Long studentId);
}
