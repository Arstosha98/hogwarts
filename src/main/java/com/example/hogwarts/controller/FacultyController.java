package com.example.hogwarts.controller;

import com.example.hogwarts.model.Faculty;
import com.example.hogwarts.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getById(@PathVariable("id") Long id){
        Faculty faculty = facultyService.getById(id);
        if (faculty == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
    @PostMapping
    public Faculty create(@RequestBody Faculty faculty){
        return facultyService.create(faculty);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Faculty> update(@PathVariable("id") Long id, @RequestBody Faculty faculty){
        Faculty foundFaculty = facultyService.update(id, faculty);
        if(foundFaculty == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAll(){
        return ResponseEntity.ok(facultyService.getAll());
    }
    @GetMapping("/filtered")
    public ResponseEntity<Collection<Faculty>> findByColor(@RequestParam(required = false) String color){
        if (color != null && !color.isBlank()){
            return ResponseEntity.ok(facultyService.findByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}