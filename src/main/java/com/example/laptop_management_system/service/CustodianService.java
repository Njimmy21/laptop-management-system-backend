package com.example.laptop_management_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.laptop_management_system.domain.Brand;
import com.example.laptop_management_system.domain.Custodian;
import com.example.laptop_management_system.domain.StoreRoom;
import com.example.laptop_management_system.repository.BrandRepository;
import com.example.laptop_management_system.repository.CustodianRepository;
import com.example.laptop_management_system.repository.StoreRoomRepository;

@Service
public class CustodianService {

    private final CustodianRepository custodianRepository;
    private final BrandRepository brandRepository;
    private final StoreRoomRepository storeRoomRepository;

    public CustodianService(CustodianRepository custodianRepository,
                            BrandRepository brandRepository,
                            StoreRoomRepository storeRoomRepository) {
        this.custodianRepository = custodianRepository;
        this.brandRepository = brandRepository;
        this.storeRoomRepository = storeRoomRepository;
    }

    public String save(Custodian custodian) {
        custodianRepository.save(custodian);
        return "Custodian saved successfully";
    }

    public List<Custodian> findAll() {
        return custodianRepository.findAll();
    }

    public Optional<Custodian> findById(UUID id) {
        return custodianRepository.findById(id);
    }

    public String update(UUID id, Custodian custodian) {
        Optional<Custodian> existing = custodianRepository.findById(id);
        if (existing.isEmpty()) {
            return "The custodian with that id does not exist";
        }
        Custodian toUpdate = existing.get();
        toUpdate.setFirstName(custodian.getFirstName());
        toUpdate.setLastName(custodian.getLastName());
        toUpdate.setEmail(custodian.getEmail());
        custodianRepository.save(toUpdate);
        return "Custodian updated successfully";
    }

    public String delete(UUID id) {
        if (!custodianRepository.existsById(id)) {
            return "The custodian with that id does not exist";
        }
        custodianRepository.deleteById(id);
        return "Custodian deleted successfully";
    }

    public List<Custodian> searchByBrandName(String name) {
        return custodianRepository.searchByBrandName(name);
    }

    public String addBrand(UUID custodianId, UUID brandId) {
        Optional<Custodian> custodian = custodianRepository.findById(custodianId);
        if (custodian.isEmpty()) {
            return "The custodian with that id does not exist";
        }
        Optional<Brand> brand = brandRepository.findById(brandId);
        if (brand.isEmpty()) {
            return "That brand does not exist";
        }
        Custodian c = custodian.get();
        boolean alreadyHas = c.getBrands().stream()
                .anyMatch(b -> b.getId().equals(brandId));
        if (alreadyHas) {
            return "The custodian already has that brand";
        }
        c.getBrands().add(brand.get()); 
        custodianRepository.save(c);
        return "Brand added to the custodian";
    }

        public String changeStoreRoom(UUID custodianId, int roomNumber) {
        Optional<Custodian> custodian = custodianRepository.findById(custodianId);
        if (custodian.isEmpty()) {
            return "The custodian with that id does not exist";
        }
        Optional<StoreRoom> room = storeRoomRepository.findByRoomNumber(roomNumber);
        if (room.isEmpty()) {
            return "The store room with that roomNumber does not exist";
        }
        if (custodianRepository.existsByStoreRoomAndIdNot(room.get(), custodianId)) {
            return "That store room is taken by another custodian";
        }
        Custodian c = custodian.get();
        c.setStoreRoom(room.get());
        custodianRepository.save(c);
        return "Store room changed successfully";
    }
}