package com.jubydull.demo.repository;

import com.jubydull.demo.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByNameIgnoreCaseAndActive(String studentName, char active);
    List<Student> findAllByActive(char active);
}
