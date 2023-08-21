package com.example.hogwarts.service;

import com.example.hogwarts.model.Student;

import java.util.Collection;

public interface StudentService {

    Student create(Student student);

    Student update(Long id, Student student);

    Student getById(Long id);

    void delete(Long id);
    Collection<Student> getAll();
    Collection<Student> getByAge(int age);
    Collection<Student> getByAgeBetween(int min, int max);
    Collection<Student> getByFacultyId(Long facultyId);
}
