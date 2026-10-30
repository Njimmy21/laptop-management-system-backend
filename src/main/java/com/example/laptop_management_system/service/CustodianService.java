package com.example.laptop_management_system.service;

import com.example.laptop_management_system.domain.Custodian;
import com.example.laptop_management_system.repository.CustodianRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustodianService {

    private final CustodianRepository custodianRepository;

    public CustodianService(CustodianRepository custodianRepository) {
        this.custodianRepository = custodianRepository;
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
}