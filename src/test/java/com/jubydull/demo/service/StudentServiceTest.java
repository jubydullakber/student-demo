package com.jubydull.demo.service;

import com.jubydull.demo.exceptions.StudentExistException;
import com.jubydull.demo.exceptions.StudentNotFoundException;
import com.jubydull.demo.model.dto.StudentDto;
import com.jubydull.demo.model.dto.StudentInfoDto;
import com.jubydull.demo.model.entity.Student;
import com.jubydull.demo.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentServiceTest {

    @Autowired
    private StudentService service;

    @MockBean
    private StudentRepository repository;

    @Test
    @DisplayName("Test getStudentByName Success")
    void testGetStudentByNameSuccess() {

        Student mockStudent = Student.builder().id(1L).name("Jubydull").active('Y').build();
        doReturn(Optional.of(mockStudent)).when(repository).findByNameIgnoreCaseAndActive("Jubydull",'Y');

        StudentInfoDto expected = StudentInfoDto.builder().id(1L).name("Jubydull").build();

        StudentInfoDto returnedStudent = service.getStudentByName("Jubydull");

        Assertions.assertSame(expected.getId(),returnedStudent.getId(),"Student ID should be the same");
        Assertions.assertSame(expected.getName(),returnedStudent.getName(),"Student Name should be the same");
    }

    @Test
    @DisplayName("Test getStudentByName Not Found")
    void testGetStudentByNameNotFound() {
        doReturn(Optional.empty()).when(repository).findByNameIgnoreCaseAndActive("Jubydull",'Y');;

        Exception exception = assertThrows(StudentNotFoundException.class, () -> {
            service.getStudentByName("Jubydull");
        });

        String expectedMessage = "No Student found with student Name : Jubydull";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Test findAll")
    void getAllStudent() {
        Student mockStudent = Student.builder().id(1L).name("Jubydull").active('Y').build();
        Student mockStudent2 = Student.builder().id(2L).name("Akber").active('Y').build();
        doReturn(Arrays.asList(mockStudent, mockStudent2)).when(repository).findAllByActive('Y');

        List<StudentInfoDto> students = service.getAllStudent();

        Assertions.assertEquals(2, students.size(), "getAllStudent should return 2 students");
    }

    @Test
    @DisplayName("Test Create Student")
    void testCreate() {
        StudentDto createStudent = StudentDto.builder().name("Jubydull").build();
        Student student = Student.builder().id(1L).name("Jubydull").active('Y').build();
        doReturn(student).when(repository).save(any());

        StudentInfoDto returnedStudent = service.createStudent(createStudent);

        Assertions.assertNotNull(returnedStudent, "The saved product should not be null");
        Assertions.assertEquals(1L, returnedStudent.getId().intValue(),
                "The Id for a new Student should be 1");
    }

    @Test
    @DisplayName("Test createStudent Already Exist")
    void testCreateStudentAlreadyExist() {
        StudentDto createStudent = StudentDto.builder().name("Jubydull").build();

        Student mockStudent = Student.builder().id(1L).name("Jubydull").active('Y').build();
        doReturn(Optional.of(mockStudent)).when(repository).findByNameIgnoreCaseAndActive("Jubydull",'Y');

        Exception exception = assertThrows(StudentExistException.class, () -> {
            service.createStudent(createStudent);
        });

        String expectedMessage = "Student Exist with student Name : Jubydull";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Test Update Student")
    void testUpdate() {
        StudentDto createStudent = StudentDto.builder().name("Jubydull").build();
        Student student = Student.builder().id(1L).name("Jubydull").active('Y').build();

        Student mockStudent = Student.builder().id(1L).name("Jubydull").active('Y').build();
        doReturn(Optional.of(mockStudent)).when(repository).findByNameIgnoreCaseAndActive("Jubydull",'Y');

        doReturn(student).when(repository).save(any());

        StudentInfoDto returnedStudent = service.updateStudent("Jubydull",createStudent);

        Assertions.assertNotNull(returnedStudent, "The saved product should not be null");
        Assertions.assertEquals(1L, returnedStudent.getId().intValue(),
                "The Id for a edited Student should be 1");
    }

    @Test
    @DisplayName("Test Delete Student")
    void testDelete() {
        Student mockStudent = Student.builder().id(1L).name("Jubydull").active('Y').build();
        doReturn(Optional.of(mockStudent)).when(repository).findByNameIgnoreCaseAndActive("Jubydull",'Y');
        String msg = service.deleteStudent("Jubydull");
        Assertions.assertNotNull(msg, "The deleted student return successful msg");
    }
}
