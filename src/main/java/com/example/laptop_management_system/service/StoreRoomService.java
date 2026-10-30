package com.example.laptop_management_system.service;

import com.example.laptop_management_system.domain.StoreRoom;
import com.example.laptop_management_system.repository.StoreRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StoreRoomService {

    private final StoreRoomRepository storeRoomRepository;

    public StoreRoomService(StoreRoomRepository storeRoomRepository) {
        this.storeRoomRepository = storeRoomRepository;
    }

    public String save(StoreRoom storeRoom) {
        storeRoomRepository.save(storeRoom);
        return "Store room saved successfully";
    }

    public List<StoreRoom> findAll() {
        return storeRoomRepository.findAll();
    }

    public Optional<StoreRoom> findById(UUID id) {
        return storeRoomRepository.findById(id);
    }

    public String update(UUID id, StoreRoom storeRoom) {
        Optional<StoreRoom> existing = storeRoomRepository.findById(id);
        if (existing.isEmpty()) {
            return "The store room with that id does not exist";
        }
        StoreRoom toUpdate = existing.get();
        toUpdate.setRoomNumber(storeRoom.getRoomNumber());
        toUpdate.setName(storeRoom.getName());
        storeRoomRepository.save(toUpdate);
        return "Store room updated successfully";
    }

    public String delete(UUID id) {
        if (!storeRoomRepository.existsById(id)) {
            return "The store room with that id does not exist";
        }
        storeRoomRepository.deleteById(id);
        return "Store room deleted successfully";
    }

    public List<StoreRoom> findFreeStoreRooms() {
        return storeRoomRepository.findFreeStoreRooms();
    }
}