package com.example.laptop_management_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.laptop_management_system.domain.StoreRoom;

public interface StoreRoomRepository extends JpaRepository<StoreRoom, UUID> {
}
