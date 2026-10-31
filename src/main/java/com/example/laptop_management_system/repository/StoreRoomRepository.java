package com.example.laptop_management_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.laptop_management_system.domain.StoreRoom;

import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface StoreRoomRepository extends JpaRepository<StoreRoom, UUID> {
    @Query("SELECT s FROM StoreRoom s WHERE NOT EXISTS " +
       "(SELECT c FROM Custodian c WHERE c.storeRoom = s) ORDER BY s.roomNumber")
    List<StoreRoom> findFreeStoreRooms();
    Optional<StoreRoom> findByRoomNumber(int roomNumber);
}
