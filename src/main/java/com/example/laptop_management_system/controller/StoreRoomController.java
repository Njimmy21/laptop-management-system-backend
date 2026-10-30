package com.example.laptop_management_system.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.laptop_management_system.domain.StoreRoom;
import com.example.laptop_management_system.service.StoreRoomService;

@RestController
@RequestMapping("/api/store-rooms")
public class StoreRoomController {

    private final StoreRoomService storeRoomService;

    public StoreRoomController(StoreRoomService storeRoomService) {
        this.storeRoomService = storeRoomService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody StoreRoom storeRoom) {
        String message = storeRoomService.save(storeRoom);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StoreRoom>> findAll() {
        return new ResponseEntity<>(storeRoomService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<StoreRoom> storeRoom = storeRoomService.findById(id);
        if (storeRoom.isEmpty()) {
            return new ResponseEntity<>("The store room with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(storeRoom.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody StoreRoom storeRoom) {
        String message = storeRoomService.update(id, storeRoom);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = storeRoomService.delete(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
