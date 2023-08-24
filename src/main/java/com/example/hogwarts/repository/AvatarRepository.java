package com.example.hogwarts.repository;

import com.example.hogwarts.model.Avatar;
import com.example.hogwarts.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional <Avatar> findFirstByStudent (Student student);
    void deleteByStudentId(Long studentId);
}
