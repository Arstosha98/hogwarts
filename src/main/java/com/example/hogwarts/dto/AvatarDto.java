package com.example.hogwarts.dto;

public class AvatarDto {
    private Long id;
    private Long studentId;
    private String StudentName;

    public AvatarDto() {
    }

    public AvatarDto(Long id, Long studentId, String studentName) {
        this.id = id;
        this.studentId = studentId;
        this.StudentName = studentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }
}