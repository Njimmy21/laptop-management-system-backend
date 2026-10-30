package com.example.laptop_management_system.repository;

import com.example.laptop_management_system.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;


public interface StudentRepository extends JpaRepository<Student, UUID> {

    List<Student> findByFacultyIgnoreCaseOrderByLastNameAsc(String faculty);
}