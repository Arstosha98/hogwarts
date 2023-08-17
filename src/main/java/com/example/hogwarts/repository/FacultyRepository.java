package com.example.hogwarts.repository;

import com.example.hogwarts.model.Faculty;
import com.example.hogwarts.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColor(String color);
    List<Faculty> filteredByColorOrName(String colorOrName);
}
