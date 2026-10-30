package com.example.laptop_management_system.controller;

import com.example.laptop_management_system.domain.Student;
import com.example.laptop_management_system.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Student student) {
        String message = studentService.save(student);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> findAll() {
        return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Student> student = studentService.findById(id);
        if (student.isEmpty()) {
            return new ResponseEntity<>("The student with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Student student) {
        String message = studentService.update(id, student);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = studentService.delete(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}