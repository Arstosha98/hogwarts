package com.example.hogwarts.service;

import com.example.hogwarts.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    
    Faculty create(Faculty faculty);
    
    Faculty update(long id, Faculty faculty);
    
    Faculty getById(long id);
    
    void delete(long id);
    Collection<Faculty> getAll();
    Collection<Faculty> findByColor(String color);
}
