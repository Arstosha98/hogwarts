package com.example.hogwarts.repository;

import com.example.hogwarts.model.Student;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);
    List<Student> findByAgeBetween(int min, int max);
    @Query(value = "select count from student",nativeQuery = true)
    Long countAll();
    @Query(value = "select avg(age) from student",nativeQuery = true)
    Double averageAge();
    @Query(value = "select from student order by id desc limit :num",nativeQuery = true)
    List<Student> findLastStudent(@Param("num") int num);
}
