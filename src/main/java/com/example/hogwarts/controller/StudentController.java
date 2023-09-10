package com.example.hogwarts.controller;

import com.example.hogwarts.model.Student;
import com.example.hogwarts.service.AvatarService;
import com.example.hogwarts.service.StudentService;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable("id") Long id){
        Student student = studentService.getById(id);
        if (student == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
    @PostMapping
    public Student create(@RequestBody Student student){
        return studentService.create(student);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable("id") Long id, @RequestBody Student student){
        Student foundStudent = studentService.update(id, student);
        if (foundStudent == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<Collection<Student>> getAll(){
        return ResponseEntity.ok(studentService.getAll());
    }
    @GetMapping("/filtered")
    public ResponseEntity<Collection<Student>> getByAge(@RequestParam(required = false) int age){
        if (age > 0){
            return ResponseEntity.ok(studentService.getByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
    @GetMapping("/age-between")
    public ResponseEntity<Collection<Student>> getByAgeBetween(@RequestParam int min, @RequestParam int max){
        if (max < min){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(studentService.getByAgeBetween(min,max));
    }

    @GetMapping("/by-faculty")
    public Collection<Student> getByFacultyId(Long facultyId){
        return studentService.getByFacultyId(facultyId);
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> save(@PathVariable Long studentId, @RequestBody MultipartFile multipartFile){
        try {
            return ResponseEntity.ok(avatarService.save(studentId, multipartFile));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/count")
    public Long count(){
        return studentService.count();
    }
    @GetMapping("/average-age")
    public Double averageAge(){
        return studentService.averageAge();
    }
    @GetMapping("/last-five")
    public List<Student> getLastStudent(){
        return studentService.getLastStudent(5);
    }
}