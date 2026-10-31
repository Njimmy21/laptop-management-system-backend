package com.example.laptop_management_system.controller;

import com.example.laptop_management_system.domain.ConditionReport;
import com.example.laptop_management_system.service.ConditionReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/condition-reports")
public class ConditionReportController {

    private final ConditionReportService conditionReportService;

    public ConditionReportController(ConditionReportService conditionReportService) {
        this.conditionReportService = conditionReportService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID loanId, @RequestBody ConditionReport report) {
        String message = conditionReportService.save(loanId, report);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.contains("only for RETURNED")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ConditionReport>> findAll() {
        return new ResponseEntity<>(conditionReportService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/student/{studentNumber}")
    public ResponseEntity<List<ConditionReport>> findHistoryOfStudent(@PathVariable String studentNumber) {
        return new ResponseEntity<>(conditionReportService.findHistoryOfStudent(studentNumber), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = conditionReportService.delete(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}