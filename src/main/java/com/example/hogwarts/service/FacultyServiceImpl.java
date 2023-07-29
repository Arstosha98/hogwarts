package com.example.hogwarts.service;

import com.example.hogwarts.exceptions.FacultyNotFoundException;
import com.example.hogwarts.model.Faculty;
import com.example.hogwarts.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty create(Faculty faculty){return facultyRepository.save(faculty);
    }
    public Faculty getById(Long id){
        return facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }
    public Faculty update(Long id, Faculty faculty){
        Faculty existingFaculty = facultyRepository.findById(id)
                .orElseThrow(FacultyNotFoundException::new);
        existingFaculty.setColor(faculty.getColor());
        existingFaculty.setName(faculty.getName());
        return facultyRepository.save(existingFaculty);
    }
    public void delete(Long id){
        Faculty faculty = facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
        facultyRepository.delete(faculty);
    }
    public Collection<Faculty> getAll(){

        return facultyRepository.findAll();
    }
    public Collection<Faculty> findByColor(String color){
        return facultyRepository.findByColor(color);
    }
}
