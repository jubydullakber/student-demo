package com.jubydull.demo.controller;

import com.jubydull.demo.model.dto.StudentDto;
import com.jubydull.demo.model.dto.StudentInfoDto;
import com.jubydull.demo.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/student")
public class StudentController {

    private StudentService studentService;


    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(description = "Find All Active Students")
    @GetMapping
    ResponseEntity<List<StudentInfoDto>> findAllStudents() {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudent());
    }

    @Operation(description = "Find Student By Name")
    @GetMapping("/{name}")
    ResponseEntity<StudentInfoDto> findStudentByName(@PathVariable("name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentByName(name));
    }

    @Operation(description = "Create a new Student")
    @PostMapping
    ResponseEntity<StudentInfoDto> createStudent(@RequestBody StudentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(dto));
    }

    @Operation(description = "Update A Student")
    @PutMapping("/{name}")
    ResponseEntity<StudentInfoDto> updateStudent(@PathVariable("name") String name, @RequestBody StudentDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.updateStudent(name, dto));
    }

    @Operation(description = "Delete A Student")
    @DeleteMapping("/{name}")
    ResponseEntity<String> deleteStudent(@PathVariable("name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.deleteStudent(name));
    }


}
