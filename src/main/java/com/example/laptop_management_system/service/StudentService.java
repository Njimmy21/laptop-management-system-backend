package com.example.laptop_management_system.service;

import com.example.laptop_management_system.domain.Student;
import com.example.laptop_management_system.repository.StudentRepository;
import org.springframework.stereotype.Service;
import com.example.laptop_management_system.repository.CustodianRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CustodianRepository custodianRepository;

    public StudentService(StudentRepository studentRepository, CustodianRepository custodianRepository) {
        this.studentRepository = studentRepository;
        this.custodianRepository = custodianRepository;
    }

    public String save(Student student) {
        studentRepository.save(student);
        return "Student saved successfully";
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(UUID id) {
        return studentRepository.findById(id);
    }

    public String update(UUID id, Student student) {
        Optional<Student> existing = studentRepository.findById(id);
        if (existing.isEmpty()) {
            return "The student with that id does not exist";
        }
        Student toUpdate = existing.get();
        toUpdate.setFirstName(student.getFirstName());
        toUpdate.setLastName(student.getLastName());
        toUpdate.setStudentNumber(student.getStudentNumber());
        toUpdate.setFaculty(student.getFaculty());
        toUpdate.setPhone(student.getPhone());
        studentRepository.save(toUpdate);
        return "Student updated successfully";
    }

    public String delete(UUID id) {
        if (!studentRepository.existsById(id)) {
            return "The student with that id does not exist";
        }
        studentRepository.deleteById(id);
        return "Student deleted successfully";
    }

    public List<Student> findByFaculty(String faculty) {
        return studentRepository.findByFacultyIgnoreCaseOrderByLastNameAsc(faculty);
    }

    public Optional<List<Student>> findOfCustodian(UUID custodianId) {
        if (!custodianRepository.existsById(custodianId)) {
            return Optional.empty();        // the custodian does not exist
        }
        return Optional.of(studentRepository.findStudentsOfCustodian(custodianId));
    }
}