package com.jubydull.demo.service;

import com.jubydull.demo.enums.StatusEnum;
import com.jubydull.demo.exceptions.StudentExistException;
import com.jubydull.demo.exceptions.StudentNotFoundException;
import com.jubydull.demo.model.StudentModelConverter;
import com.jubydull.demo.model.dto.StudentDto;
import com.jubydull.demo.model.dto.StudentInfoDto;
import com.jubydull.demo.model.entity.Student;
import com.jubydull.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentInfoDto> getAllStudent() {
        List<Student> students = studentRepository.findAllByActive(StatusEnum.ACTIVE.getStatus());
        List<StudentInfoDto> infoDtos = StudentModelConverter.convertStudentListtoStudentInfoDtos(students);
        return infoDtos;
    }

    @Override
    public StudentInfoDto getStudentByName(String studentName) {
        Student student = studentRepository
                .findByNameIgnoreCaseAndActive(studentName, StatusEnum.ACTIVE.getStatus())
                .orElseThrow(() -> new StudentNotFoundException("No Student found with student Name : " + studentName));
        StudentInfoDto dto = StudentModelConverter.convertStudentToStudentInfoDto(student);
        return dto;
    }

    @Override
    public StudentInfoDto createStudent(StudentDto dto) {
        Optional<Student> studentExist = studentRepository
                .findByNameIgnoreCaseAndActive(dto.getName(), StatusEnum.ACTIVE.getStatus());

        if (studentExist.isPresent()) {
            throw new StudentExistException("Student Exist with student Name : " + dto.getName());
        }
        Student student = StudentModelConverter.convertStudentDtoToStudentEntity(dto);
        Student studentSaved = studentRepository.save(student);
        StudentInfoDto studentInfoDto = StudentModelConverter.convertStudentToStudentInfoDto(studentSaved);
        return studentInfoDto;

    }

    @Override
    public StudentInfoDto updateStudent(String studentName, StudentDto dto) {
        Student student = studentRepository
                .findByNameIgnoreCaseAndActive(studentName, StatusEnum.ACTIVE.getStatus())
                .orElseThrow(() -> new StudentNotFoundException("No Student found with student Name : " + studentName));

        student.setName(dto.getName());
        studentRepository.save(student);
        StudentInfoDto infoDto = StudentModelConverter.convertStudentToStudentInfoDto(student);
        return infoDto;
    }

    @Override
    public String deleteStudent(String studentName) {
        Student student = studentRepository
                .findByNameIgnoreCaseAndActive(studentName, StatusEnum.ACTIVE.getStatus())
                .orElseThrow(() -> new StudentNotFoundException("No Student found with student Name : " + studentName));

        student.setActive(StatusEnum.INACTIVE.getStatus());
        studentRepository.save(student);
        return "Student Deleted Successfully with name : " + studentName;
    }

}
