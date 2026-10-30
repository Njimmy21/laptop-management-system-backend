package com.example.laptop_management_system.repository;

import com.example.laptop_management_system.domain.Custodian;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import java.util.UUID;

public interface CustodianRepository extends JpaRepository<Custodian, UUID> {
    @Query("SELECT DISTINCT c FROM Custodian c JOIN c.brands b " +
       "WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Custodian> searchByBrandName(@Param("name") String name);
}