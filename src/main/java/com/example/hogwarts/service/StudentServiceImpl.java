package com.example.hogwarts.service;

import com.example.hogwarts.exceptions.StudentNotFoundException;
import com.example.hogwarts.model.Student;
import com.example.hogwarts.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
    }
    public Collection<Student> getAll(){

        return studentRepository.findAll();
    }
    public Collection<Student> findByAge(int age){
        return studentRepository.findByAge(age);
    }
}
