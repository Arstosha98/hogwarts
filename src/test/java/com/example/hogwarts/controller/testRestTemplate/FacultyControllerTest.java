package com.example.hogwarts.controller.testRestTemplate;

import com.example.hogwarts.HogwartsApplication;
import com.example.hogwarts.model.Faculty;
import com.example.hogwarts.model.Student;
import com.example.hogwarts.repository.FacultyRepository;
import com.example.hogwarts.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = HogwartsApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    public static final Faculty PHILOSOPHY = new Faculty(null,"philosophy", "brown");
    public static final Faculty PHYSICS = new Faculty(null,"physics", "blue");
    @Autowired
    TestRestTemplate template;
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    StudentRepository studentRepository;
    @BeforeEach
    void init(){
        template.postForEntity("/faculty", PHILOSOPHY, Faculty.class);
        template.postForEntity("/faculty", PHYSICS, Faculty.class);
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
    @Test
    void create(){
        ResponseEntity<Faculty> response = createFaculty("math","black");

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("math");
        assertThat(response.getBody().getColor()).isEqualTo("black");
    }
    @Test
    void getById(){
        ResponseEntity<Faculty> faculty = createFaculty("math","black");
        assertThat(faculty.getBody()).isNotNull();
        Long id = faculty.getBody().getId();

        ResponseEntity<Faculty> response = template.getForEntity("/faculty/" + id, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getName()).isEqualTo("math");
        assertThat(response.getBody().getColor()).isEqualTo("black");
    }
    @Test
    void getAll(){
        ResponseEntity<Collection> response = template.getForEntity("/faculty", Collection.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        Collection<Faculty> body = response.getBody();
        assertThat(body.isEmpty()).isFalse();
        assertThat(body.size()).isEqualTo(2);

    }
    @Test
    void update(){
        ResponseEntity<Faculty> response = createFaculty("math", "black");

        assertThat(response.getBody()).isNotNull();
        Faculty faculty = response.getBody();
        faculty.setColor("red");

        template.put("/faculty/" + faculty.getId(), faculty);

        response = template.getForEntity("/faculty/" + faculty.getId(), Faculty.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getColor()).isEqualTo("red");
    }
    @Test
    void delete(){
        ResponseEntity<Faculty> response = createFaculty("math", "black");

        assertThat(response.getBody()).isNotNull();
        template.delete("/faculty/" + response.getBody().getId());

        response = template.getForEntity("/faculty/" + response.getBody().getId(), Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    void filtered(){
        ResponseEntity<Collection> response = template
                .getForEntity("/faculty/filtered?color=blue", Collection.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
    }
    @Test
    void byColorOrName(){
        ResponseEntity<Collection> response = template
                .getForEntity("/faculty/by-color-or-name?colorOrName=physics", Collection.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
    }

    @Test
    void findByStudent(){
        ResponseEntity<Faculty> response = createFaculty("math", "black");
        Faculty expectedfaculty = response.getBody();
        Student student = new Student();
        student.setFaculty(expectedfaculty);
        ResponseEntity<Student> studentResp = template.postForEntity("/student", student, Student.class);
        assertThat(studentResp.getBody()).isNotNull();
        Long studentId = studentResp.getBody().getId();

        response = template.getForEntity("/faculty/by-student?studentId=" + studentId, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(expectedfaculty);
    }
}