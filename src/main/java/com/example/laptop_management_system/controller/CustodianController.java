package com.example.laptop_management_system.controller;

import com.example.laptop_management_system.domain.Custodian;
import com.example.laptop_management_system.domain.Loan;
import com.example.laptop_management_system.service.CustodianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/custodians")
public class CustodianController {

    private final CustodianService custodianService;

    public CustodianController(CustodianService custodianService) {
        this.custodianService = custodianService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Custodian custodian) {
        String message = custodianService.save(custodian);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Custodian>> findAll() {
        return new ResponseEntity<>(custodianService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Custodian> custodian = custodianService.findById(id);
        if (custodian.isEmpty()) {
            return new ResponseEntity<>("The custodian with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(custodian.get(), HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Custodian custodian) {
        String message = custodianService.update(id, custodian);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = custodianService.delete(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}