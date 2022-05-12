package com.jubydull.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jubydull.demo.exceptions.StudentExistException;
import com.jubydull.demo.exceptions.StudentNotFoundException;
import com.jubydull.demo.model.dto.StudentDto;
import com.jubydull.demo.model.dto.StudentInfoDto;
import com.jubydull.demo.model.entity.Student;
import com.jubydull.demo.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {
    @MockBean
    private StudentService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/v1/student/Jubydull - Found")
    void testGetStudentByIdFound() throws Exception {
        StudentInfoDto mockStudent = StudentInfoDto.builder().id(1L).name("Jubydull").build();
        doReturn(mockStudent).when(service).getStudentByName("Jubydull");

        mockMvc.perform(get("/api/v1/student/{name}", "Jubydull")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Jubydull")));

    }

    @Test
    @DisplayName("GET /api/v1/student/Jubydull - Not Found")
    void testGetStudentByIdNotFound() throws Exception {
        doThrow(new StudentNotFoundException("No Student found with student Name : Jubydull")).when(service).getStudentByName("Jubydull");

        mockMvc.perform(get("/api/v1/student/{name}", "Jubydull")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/v1/student/ - Already Exist")
    void testStudentPostAlreadyExist() throws Exception {
        StudentDto putStudent = StudentDto.builder().name("Jubydull").build();
        doThrow(new StudentExistException("Student Exist with student Name : Jubydull")).when(service).updateStudent("Jubydull",putStudent);

        mockMvc.perform(put("/api/v1/student/{name}","Jubydull")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(putStudent)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("POST /api/v1/student/ - Success")
    void testCreateStudent() throws Exception {
        StudentDto postStudent = StudentDto.builder().name("Jubydull").build();
        StudentInfoDto mockStudent = StudentInfoDto.builder().id(1L).name("Jubydull").build();
        doReturn(mockStudent).when(service).createStudent(postStudent);

        mockMvc.perform(post("/api/v1/student/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postStudent)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Jubydull")));
    }

    @Test
    @DisplayName("PUT /api/v1/student/Jubydull - Success")
    void testStudentPutSuccess() throws Exception {
        StudentDto putStudent = StudentDto.builder().name("Jubydull").build();
        StudentInfoDto mockStudent = StudentInfoDto.builder().id(1L).name("Jubydull").build();
        doReturn(mockStudent).when(service).updateStudent("Jubydull", putStudent);

        mockMvc.perform(put("/api/v1/student/{name}", "Jubydull")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(putStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Jubydull")));
    }


    @Test
    @DisplayName("PUT /api/v1/student/ - Not Found")
    void testStudentPutNotFound() throws Exception {
        StudentDto putStudent = StudentDto.builder().name("Jubydull").build();
        doThrow(new StudentNotFoundException("No Student found with student Name : Jubydull")).when(service).updateStudent("Jubydull", putStudent);

        mockMvc.perform(put("/api/v1/student/{name}", "Jubydull")
                        .content(asJsonString(putStudent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/student/Jubydull - Success")
    void testStudentDeleteSuccess() throws Exception {
        doReturn("Student Deleted Successfully with name : Jubydull").when(service).deleteStudent("Jubydull");

        mockMvc.perform(delete("/api/v1/student/{name}", "Jubydull")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Student Deleted Successfully with name : Jubydull")));
    }

    @Test
    @DisplayName("DELETE /api/v1/student/jubydull - Is not found")
    void testStudentDeleteFailure() throws Exception {
        doThrow(new StudentNotFoundException("No Student found with student Name : Jubydull"))
                .when(service).deleteStudent("Jubydull");

        mockMvc.perform(delete("/{name}", "Jubydull")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
