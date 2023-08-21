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
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    public StudentServiceImpl(StudentRepository studentRepository,
                              FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student create(Student student){
        return studentRepository.save(student);
    }
    public Student getById(Long id){
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }
    public Student update(Long id, Student student){
        Student existingStudent = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        existingStudent.setAge(student.getAge());
        existingStudent.setName(student.getName());
        return studentRepository.save(existingStudent);
    }
    public void delete(Long id){
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentRepository.delete(student);
    }
    public Collection<Student> getAll(){
        return studentRepository.findAll();
    }
    public Collection<Student> getByAge(int age){
        return studentRepository.findByAge(age);
    }
    public Collection<Student> getByAgeBetween(int min, int max){
        return studentRepository.findByAgeBetween(min, max);
    }
    public Collection<Student> getByFacultyId(Long facultyId){
        return facultyRepository.findById(facultyId)
                .map(Faculty::getStudent)
                .orElseThrow(FacultyNotFoundException::new);
    }
}