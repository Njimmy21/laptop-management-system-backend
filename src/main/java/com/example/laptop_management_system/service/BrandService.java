package com.example.laptop_management_system.service;

import com.example.laptop_management_system.domain.Brand;
import com.example.laptop_management_system.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public String save(Brand brand) {
        brandRepository.save(brand);
        return "Brand saved successfully";
    }

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Optional<Brand> findById(UUID id) {
        return brandRepository.findById(id);
    }

    public String update(UUID id, Brand brand) {
        Optional<Brand> existing = brandRepository.findById(id);
        if (existing.isEmpty()) {
            return "The brand with that id does not exist";
        }
        Brand toUpdate = existing.get();
        toUpdate.setName(brand.getName());
        brandRepository.save(toUpdate);
        return "Brand updated successfully";
    }

    public String delete(UUID id) {
        if (!brandRepository.existsById(id)) {
            return "The brand with that id does not exist";
        }
        brandRepository.deleteById(id);
        return "Brand deleted successfully";
    }

    public List<Brand> findUnusedBrands() {
        return brandRepository.findUnusedBrands();
    }
}