package com.example.hogwarts.service;

import com.example.hogwarts.model.Student;

import java.util.Collection;

public interface StudentService {

    Student create(Student student);

    Student update(long id, Student student);

    Student getById(long id);

    void delete(long id);
    Collection<Student> getAll();
    Collection<Student> findByAge(int age);
}
