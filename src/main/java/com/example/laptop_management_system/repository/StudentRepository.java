package com.example.laptop_management_system.repository;

import com.example.laptop_management_system.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import java.util.UUID;



public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findByFacultyIgnoreCaseOrderByLastNameAsc(String faculty);
    @Query("SELECT DISTINCT l.student FROM Loan l WHERE l.custodian.id = :custodianId")
    List<Student> findStudentsOfCustodian(@Param("custodianId") UUID custodianId);
}