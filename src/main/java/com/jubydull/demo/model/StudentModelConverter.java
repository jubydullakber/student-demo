package com.jubydull.demo.model;

import com.jubydull.demo.enums.StatusEnum;
import com.jubydull.demo.model.dto.StudentDto;
import com.jubydull.demo.model.dto.StudentInfoDto;
import com.jubydull.demo.model.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentModelConverter {
    public static Student convertStudentDtoToStudentEntity(StudentDto dto) {
        Student student = Student.builder().name(dto.getName()).active(StatusEnum.ACTIVE.getStatus()).build();
        return student;
    }

    public static StudentInfoDto convertStudentToStudentInfoDto(Student student) {
        StudentInfoDto info = StudentInfoDto.builder().name(student.getName()).id(student.getId()).build();
        return info;
    }

    public static List<StudentInfoDto> convertStudentListtoStudentInfoDtos(List<Student> students) {
        List<StudentInfoDto> infoDtos = new ArrayList<>();
        for (Student student : students) {
            StudentInfoDto infoDto = StudentInfoDto.builder().name(student.getName()).id(student.getId()).build();
            infoDtos.add(infoDto);
        }
        return infoDtos;
    }
}
