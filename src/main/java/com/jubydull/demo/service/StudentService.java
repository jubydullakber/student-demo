package com.jubydull.demo.service;

import com.jubydull.demo.model.dto.StudentDto;
import com.jubydull.demo.model.dto.StudentInfoDto;

import java.util.List;

public interface StudentService {
    List<StudentInfoDto> getAllStudent();
    StudentInfoDto getStudentByName(String studentName);
    StudentInfoDto createStudent(StudentDto dto);
    StudentInfoDto updateStudent(String studentName, StudentDto dto);
    String deleteStudent(String studentName);

}
