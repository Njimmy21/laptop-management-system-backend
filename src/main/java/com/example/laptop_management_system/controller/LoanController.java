package com.example.laptop_management_system.controller;

import com.example.laptop_management_system.domain.Loan;
import com.example.laptop_management_system.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Loan loan) {
        String message = loanService.save(loan);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Loan>> findAll() {
        return new ResponseEntity<>(loanService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Loan> loan = loanService.findById(id);
        if (loan.isEmpty()) {
            return new ResponseEntity<>("The loan with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(loan.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Loan loan) {
        String message = loanService.update(id, loan);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = loanService.delete(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}