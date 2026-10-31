package com.example.laptop_management_system.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.laptop_management_system.domain.Custodian;
import com.example.laptop_management_system.service.CustodianService;

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

    @GetMapping("/by-brand")
    public ResponseEntity<List<Custodian>> searchByBrandName(@RequestParam String name) {
        return new ResponseEntity<>(custodianService.searchByBrandName(name), HttpStatus.OK);
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

    @PutMapping("/{custodianId}/brands/{brandId}")
    public ResponseEntity<String> addBrand(@PathVariable UUID custodianId, @PathVariable UUID brandId) {
        String message = custodianService.addBrand(custodianId, brandId);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.contains("already has")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PatchMapping("/{custodianId}/store-room")
    public ResponseEntity<String> changeStoreRoom(@PathVariable UUID custodianId, @RequestParam int roomNumber) {
        String message = custodianService.changeStoreRoom(custodianId, roomNumber);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.contains("taken by another")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    
}