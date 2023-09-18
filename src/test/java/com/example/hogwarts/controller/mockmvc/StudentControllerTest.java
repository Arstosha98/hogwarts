package com.example.hogwarts.controller.mockmvc;

import com.example.hogwarts.controller.StudentController;
import com.example.hogwarts.model.Faculty;
import com.example.hogwarts.model.Student;
import com.example.hogwarts.repository.AvatarRepository;
import com.example.hogwarts.repository.FacultyRepository;
import com.example.hogwarts.repository.StudentRepository;
import com.example.hogwarts.service.AvatarService;
import com.example.hogwarts.service.StudentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.*;

import static org.mockito.Mockito.*;

@WebMvcTest(StudentController.class)
@SpyBean(StudentServiceImpl.class)
@MockBean(AvatarService.class)
@MockBean(AvatarRepository.class)
public class StudentControllerTest {
    @MockBean
    StudentRepository studentRepository;
    @MockBean
    FacultyRepository facultyRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getById() throws Exception {
        Student student = new Student(1L,"ivan",20);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("ivan"))
                .andExpect(jsonPath("$.age").value(20));
    }
    @Test
    void create() throws Exception{
        Student student = new Student(1L,"ivan",20);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/student")
                .content(objectMapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("ivan"))
                .andExpect(jsonPath("$.age").value(20));
    }
    @Test
    void update()throws Exception{
        Student student = new Student(1L,"ivan",20);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(put("/student/" + student.getId())
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("ivan"))
                .andExpect(jsonPath("$.age").value(20));
    }
    @Test
    void delete_student() throws Exception{
        Student student = new Student(1L,"ivan",20);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(delete("/student/" + student.getId())
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // (+check) .andExpect
    }
    @Test
    void getAll() throws Exception{
        when(studentRepository.findAll()).thenReturn(Arrays.asList(
                new Student(1L,"ivan", 20),
                new Student(2L,"oleg",25)));

        mockMvc.perform(get("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }
    @Test
    void getByAge() throws Exception{
        when(studentRepository.findByAge(20)).thenReturn(Arrays.asList(
                new Student(1L,"ivan", 20),
                new Student(2L,"oleg",25),
                new Student(3L,"marina",20)));

        mockMvc.perform(get("/student/filtered?age=20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[2].id").value(3L));
    }
    @Test
    void findByAgeBetween() throws Exception{
        when(studentRepository.findByAgeBetween(0,20)).thenReturn(Arrays.asList(
                new Student(1L,"ivan", 20),
                new Student(2L,"oleg",25)));

        mockMvc.perform(get("/student/age-between?min=0&max=20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("ivan"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("oleg"));
    }
    @Test
    void getByFacultyId() throws Exception{
        List<Student> students = Arrays.asList(
                new Student(1L,"ivan", 20),
                new Student(2L,"oleg",25));

        Faculty faculty= new Faculty(1L, "math","red");
        faculty.setStudent(students);
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/student/by-faculty?facultyId=" + faculty.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$").isArray())
                        .andExpect(jsonPath("$[0].id").value(1L))
                        .andExpect(jsonPath("$[0].name").value("ivan"))
                        .andExpect(jsonPath("$[1].id").value(2L))
                        .andExpect(jsonPath("$[1].name").value("oleg"));
    }
}
