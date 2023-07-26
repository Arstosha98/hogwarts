package com.example.hogwarts.service;

import com.example.hogwarts.model.Student;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{
    private final Map<Long, Student> storage = new HashMap<>();
    private long count = 0;

    public Student create(Student student){
        student.setId(count++);
        storage.put(student.getId(), student);
        return student;
    }
    public Student getById(long id){
        return storage.get(id);
    }
    public Student update(long id, Student student){
        if (!storage.containsKey(id)){
            return null;
        }
        storage.put(id,student);
        return student;
    }
    public void delete(long id){
        storage.remove(id);
    }
    public Collection<Student> getAll(){
        return storage.values();
    }
    public Collection<Student> findByAge(int age){
        return storage.values().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());
    }
}
