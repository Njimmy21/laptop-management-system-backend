package com.example.laptop_management_system.repository;

import com.example.laptop_management_system.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
}