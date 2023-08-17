package com.example.hogwarts.service;

import com.example.hogwarts.exceptions.FacultyNotFoundException;
import com.example.hogwarts.exceptions.StudentNotFoundException;
import com.example.hogwarts.model.Faculty;
import com.example.hogwarts.model.Student;
import com.example.hogwarts.repository.FacultyRepository;
import com.example.hogwarts.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository,
                              StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
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
    public Collection<Faculty> getByColor(String color){
        return facultyRepository.findByColor(color);
    }
    public Collection<Faculty> filteredByColorOrName(String colorOrName){
        return facultyRepository.filteredByColorOrName(colorOrName);
    }
    public Faculty getByStudentId(Long studentId){
        return studentRepository.findById(studentId)
                .map(Student::getFaculty)
                .orElseThrow(StudentNotFoundException::new);
    }
}
