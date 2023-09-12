package com.example.hogwarts.service;

import com.example.hogwarts.exceptions.FacultyNotFoundException;
import com.example.hogwarts.exceptions.StudentNotFoundException;
import com.example.hogwarts.model.Faculty;
import com.example.hogwarts.model.Student;
import com.example.hogwarts.repository.AvatarRepository;
import com.example.hogwarts.repository.FacultyRepository;
import com.example.hogwarts.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final AvatarRepository avatarRepository;
    public StudentServiceImpl(StudentRepository studentRepository,
                              FacultyRepository facultyRepository,
                              AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.avatarRepository = avatarRepository;
    }

    public Student create(Student student){
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }
    public Student getById(Long id){
        logger.info("Was invoked method for getById student");
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }
    public Student update(Long id, Student student){
        logger.info("Was invoked method for update student");
        Student existingStudent = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        existingStudent.setAge(student.getAge());
        existingStudent.setName(student.getName());
        return studentRepository.save(existingStudent);
    }
    @Transactional
    public void delete(Long id){
        logger.info("Was invoked method for delete student");
        avatarRepository.deleteByStudentId(id);
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentRepository.delete(student);
    }
    public Collection<Student> getAll() {
        logger.info("Was invoked method for getAll student");
        return studentRepository.findAll();
    }
    public Collection<Student> getByAge(int age){
        logger.info("Was invoked method for getByAge student");
        return studentRepository.findByAge(age);
    }
    public Collection<Student> getByAgeBetween(int min, int max){
        logger.info("Was invoked method for getByAgeBetween student");
        return studentRepository.findByAgeBetween(min, max);
    }
    public Collection<Student> getByFacultyId(Long facultyId){
        logger.info("Was invoked method for getByFacultyId");
        return facultyRepository.findById(facultyId)
                .map(Faculty::getStudent)
                .orElseThrow(FacultyNotFoundException::new);
    }
    public Long count(){
        logger.info("Was invoked method for count");
        return studentRepository.countAll();
    }
    public Double averageAge(){
        logger.info("Was invoked method for averageAge student");
        return studentRepository.averageAge();
    }
    public List<Student> getLastStudent(int num){
        logger.info("Was invoked method for getLastStudent");
        return studentRepository.findLastStudent(num);
    }
    public List<String> getNameStartedBy(char firstSymbol){
        logger.info("Was invoked method for getNameStartedBy");
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .filter(n -> Character.toLowerCase(n.charAt(0))
                        == Character.toLowerCase(firstSymbol))
                .collect(Collectors.toList());
    }
    public double getAverageAge(){
        logger.info("Was invoked method for getAverageAge");
        return studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElseThrow(StudentNotFoundException::new);
    }
    public void printAsync(){
        logger.info("Was invoked method for printAsync");
        List<Student> all = studentRepository.findAll();
        System.out.println(all.get(0).getName());
        System.out.println(all.get(1).getName());

        new Thread(()->{
            System.out.println(all.get(2).getName());
            System.out.println(all.get(3).getName());
        }).start();

        new Thread(()->{
            System.out.println(all.get(4).getName());
            System.out.println(all.get(5).getName());
        }).start();
    }
    public void printSync(){
        logger.info("Was invoked method for printSync");
        List<Student> all = studentRepository.findAll();
        printSync(all.get(0).getName());
        printSync(all.get(1).getName());

        new Thread(()->{
            printSync(all.get(2).getName());
            printSync(all.get(3).getName());
        }).start();

        new Thread(()->{
            printSync(all.get(4).getName());
            printSync(all.get(5).getName());
        }).start();
    }
    private synchronized void printSync(String name){
        System.out.println(name);
    }
}