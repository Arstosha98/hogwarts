package com.example.hogwarts.controller.mockmvc;

import com.example.hogwarts.controller.FacultyController;
import com.example.hogwarts.model.Faculty;
import com.example.hogwarts.model.Student;
import com.example.hogwarts.repository.FacultyRepository;
import com.example.hogwarts.repository.StudentRepository;
import com.example.hogwarts.service.FacultyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTest {
    @SpyBean
    FacultyServiceImpl facultyService;
    @MockBean
    FacultyRepository facultyRepository;
    @MockBean
    StudentRepository studentRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getById() throws Exception{
        Faculty faculty = new Faculty(1L, "math","red");
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("math"))
                .andExpect(jsonPath("$.color").value("red"));
    }
    @Test
    void create() throws Exception{
        Faculty faculty = new Faculty(1L, "math","red");
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("math"))
                .andExpect(jsonPath("$.color").value("red"));
    }
    @Test
    void update() throws Exception{
        Faculty faculty = new Faculty(1L, "math","red");
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(put("/faculty/" + faculty.getId())
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("math"))
                .andExpect(jsonPath("$.color").value("red"));
    }
    @Test
    void delete_faculty() throws Exception{
        Faculty faculty = new Faculty(1L, "math","red");
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(delete("/faculty/" + faculty.getId())
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void getAll() throws Exception{
        when(facultyRepository.findAll()).thenReturn(Arrays.asList(
                new Faculty(1L,"math","red"),
                new Faculty(2L,"biology","green")));

        mockMvc.perform(get("/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }
    @Test
    void getByColor() throws Exception{
        when(facultyRepository.findByColor("red")).thenReturn(Arrays.asList(
                new Faculty(1L,"math","red"),
                new Faculty(2L,"algebra","red"),
                new Faculty(3L,"biology","green")));

        mockMvc.perform(get("/faculty/filtered?color=red")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3));
    }
    @Disabled
    @Test
    void filteredByColorOrName() throws Exception{
        List<Faculty> faculty = List.of(
                new Faculty(1L,"math","red"),
                new Faculty(2L,"rus","yellow"));
        when(facultyRepository.findAllByColorLikeIgnoreCaseOrNameLikeIgnoreCase("red","math"))
                .thenReturn(faculty);

        mockMvc.perform(get("/faculty/by-color-or-name")
                        .param("colorOrName","red")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("math"))
                .andExpect(jsonPath("$[0].color").value("red"));
    }
    @Test
    void findByStudent() throws Exception{
        Faculty faculty = new Faculty(1L, "math","red");
        Student student = new Student(1L,"ivan", 20);
        student.setFaculty(faculty);
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        mockMvc.perform(get("/faculty/by-student?studentId=" + student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("math"))
                .andExpect(jsonPath("$.color").value("red"));
    }
}
