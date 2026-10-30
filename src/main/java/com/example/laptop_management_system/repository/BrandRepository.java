package com.example.laptop_management_system.repository;

import com.example.laptop_management_system.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
    @Query("SELECT b FROM Brand b WHERE b.custodians IS EMPTY")
    List<Brand> findUnusedBrands();
}