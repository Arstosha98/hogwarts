package com.example.hogwarts.service;

import com.example.hogwarts.exceptions.FacultyNotFoundException;
import com.example.hogwarts.exceptions.StudentNotFoundException;
import com.example.hogwarts.model.Faculty;
import com.example.hogwarts.model.Student;
import com.example.hogwarts.repository.FacultyRepository;
import com.example.hogwarts.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository,
                              StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty create(Faculty faculty){
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }
    public Faculty getById(Long id){
        logger.info("Was invoked method for getById faculty");
        return facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }
    public Faculty update(Long id, Faculty faculty){
        logger.info("Was invoked method for update faculty");
        Faculty existingFaculty = facultyRepository.findById(id)
                .orElseThrow(FacultyNotFoundException::new);
        existingFaculty.setColor(faculty.getColor());
        existingFaculty.setName(faculty.getName());
        return facultyRepository.save(existingFaculty);
    }
    public void delete(Long id){
        logger.info("Was invoked method for delete faculty");
        Faculty faculty = facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
        facultyRepository.delete(faculty);
    }
    public Collection<Faculty> getAll(){
        logger.info("Was invoked method for getAll faculty");
        return facultyRepository.findAll();
    }
    public Collection<Faculty> getByColor(String color){
        logger.info("Was invoked method for getByColor faculty");
        return facultyRepository.findByColor(color);
    }
    public Collection<Faculty> filteredByColorOrName(String color, String name){
        logger.info("Was invoked method for filteredByColorOrName faculty");
        return facultyRepository.findAllByColorLikeIgnoreCaseOrNameLikeIgnoreCase(color,name);
    }
    public Faculty getByStudentId(Long studentId){
        logger.info("Was invoked method for getByStudentId");
        return studentRepository.findById(studentId)
                .map(Student::getFaculty)
                .orElseThrow(StudentNotFoundException::new);
    }
}
