package com.jubydull.demo.repository;


import com.jubydull.demo.model.entity.Student;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository repository;

    @BeforeEach
    void init(){
        repository.deleteAll();
        Student student = Student.builder().name("Jubydull").active('Y').build();
        Student student1 = Student.builder().name("Akber").active('Y').build();
        Student student2 = Student.builder().name("Shajib").active('N').build();
        List<Student> students = List.of(student,student1,student2);
        repository.saveAll(students);
    }

    @AfterEach
    void destroy(){
        repository.deleteAll();
    }
    @Test
    void testFindAll() {
        List<Student> students = repository.findAll();
        Assertions.assertEquals(3, students.size(), "We should have 3 Students in our database");
    }

    @Test
    void testFindActiveStudentSuccess() {
        List<Student> students = repository.findAllByActive('Y');
        Assertions.assertEquals(2, students.size(), "We should have 2 Active Students in our database");
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Student> Student = repository.findById(4L);
        Assertions.assertFalse(Student.isPresent(), "Student with ID 4 should be not be found");
    }

    @Test
    void testSave() {
        Student student = Student.builder().name("John").active('Y').build();
        Student savedStudent = repository.save(student);

        Assertions.assertEquals("John", savedStudent.getName());

        Optional<Student> loadedStudent = repository.findById(savedStudent.getId());
        Assertions.assertTrue(loadedStudent.isPresent(), "Reload Student from the database");
        Assertions.assertEquals("John", loadedStudent.get().getName(), "Student name match");
    }


    @Test
    void testDeleteSuccess() {
        Student student = Student.builder().id(1L).name("Jubydull").active('Y').build();
        repository.delete(student);

        Optional<Student> Student = repository.findById(1l);
        Assertions.assertFalse(Student.isPresent(), "Student with ID 1 should have been deleted");
    }

    @Test
    void testFindStudentByName() {
        Optional<Student> loadedStudent = repository.findByNameIgnoreCaseAndActive
                ("JubyDull", 'Y');
        Assertions.assertTrue(loadedStudent.isPresent(), "Student Found in database");
        Assertions.assertEquals("Jubydull", loadedStudent.get().getName(), "Student name match");

    }
}
