package com.example.hogwarts.controller.testRestTemplate;

import com.example.hogwarts.HogwartsApplication;
import com.example.hogwarts.model.Faculty;
import com.example.hogwarts.model.Student;
import com.example.hogwarts.repository.FacultyRepository;
import com.example.hogwarts.repository.StudentRepository;
import com.example.hogwarts.service.AvatarService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = HogwartsApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    public static final Student IVAN = new Student(null,"ivan",15);
    public static final Student FEDOR = new Student(null,"fedor",20);
    public static final Student MARINA = new Student(null,"marina",25);

    @Autowired
    TestRestTemplate template;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    AvatarService avatarService;

    @BeforeEach
    void init(){
        template.postForEntity("/student", IVAN, Student.class);
        template.postForEntity("/student", FEDOR, Student.class);
        template.postForEntity("/student", MARINA, Student.class);
    }
    @AfterEach
    void clearDB(){
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    private ResponseEntity<Faculty> createFaculty(String name, String color){
        Faculty request = new Faculty();
        request.setName(name);
        request.setColor(color);

        ResponseEntity<Faculty> response = template.postForEntity("/faculty", request, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        return response;
    }
    private ResponseEntity<Student> createStudent(long id, String name, int age){
        Student request = new Student();
        request.setName(name);
        request.setAge(age);

        ResponseEntity<Student> response = template.postForEntity("/student", request, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        return response;
    }
    @Test
    void create(){
        ResponseEntity<Student> response = createStudent(1L,"ivan",15);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("ivan");
        assertThat(response.getBody().getAge()).isEqualTo(15);
    }
    @Test
    void getById(){
        ResponseEntity<Student> student = createStudent(1L,"ivan",15);
        assertThat(student.getBody()).isNotNull();
        Long id = student.getBody().getId();

        ResponseEntity<Student> response = template.getForEntity("/student/" + id, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getName()).isEqualTo("ivan");
        assertThat(response.getBody().getAge()).isEqualTo(15);
    }
    @Test
    void getAll(){
        ResponseEntity<Collection> response = template.getForEntity("/student", Collection.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        Collection<Student> body = response.getBody();
        assertThat(body.isEmpty()).isFalse();
        assertThat(body.size()).isEqualTo(3);
    }
    @Test
    void update(){
        ResponseEntity<Student> response = createStudent(1L,"ivan",15);
        assertThat(response.getBody()).isNotNull();

        Student student = response.getBody();
        student.setAge(30);

        template.put("/student/" + student.getId(), student);

        response = template.getForEntity("/student/" + student.getId(), Student.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAge()).isEqualTo(30);
    }
    @Test
    void delete(){
        ResponseEntity<Student> response = createStudent(1L,"ivan",15);

        assertThat(response.getBody()).isNotNull();
        template.delete("/student/" + response.getBody().getId());

        response = template.getForEntity("/student/" + response.getBody().getId(), Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    void getByAgeBetween(){
        ResponseEntity<Collection> response = template.getForEntity("/student/age-between?min=10&max=30", Collection.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(3);
    }
    @Disabled
    @Test
    void getByFacultyId(){
        ResponseEntity<Faculty> faculty = createFaculty("math", "red");
        Faculty expectedfaculty = faculty.getBody();
        Student student = new Student(1L,"ivan",15);
        student.setFaculty(expectedfaculty);

        ResponseEntity<Student> studentResp = template.postForEntity("/student", student, Student.class);
        assertThat(studentResp.getBody()).isNotNull();
        Long facultyId = studentResp.getBody().getFaculty().getId();

        ResponseEntity<Student> response = template.getForEntity("/student/by-facultyId?facultyId=" + facultyId, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(expectedfaculty);
    }
}
