package com.example.hogwarts.service;

import com.example.hogwarts.model.Faculty;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> storage = new HashMap<>();
    private long count = 0;

    public Faculty create(Faculty faculty){
        faculty.setId(count++);
        storage.put(faculty.getId(), faculty);
        return faculty;
    }
    public Faculty getById(long id){
        return storage.get(id);
    }
    public Faculty update(long id, Faculty faculty){
        if (!storage.containsKey(id)){
            return null;
        }
        storage.put(id,faculty);
        return faculty;
    }
    public void delete(long id){
        storage.remove(id);
    }
    public Collection<Faculty> getAll(){
        return storage.values();
    }
    public Collection<Faculty> findByColor(String color){
        return storage.values().stream()
                .filter(f -> f.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }
}
