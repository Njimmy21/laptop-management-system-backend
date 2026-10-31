package com.example.laptop_management_system.repository;

import com.example.laptop_management_system.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;



import java.time.LocalDate;
import java.util.UUID;
import com.example.laptop_management_system.domain.LoanStatus;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
    List<Loan> findByStatusOrderByLoanDateAsc(LoanStatus status);
    List<Loan> findByLoanDateBetweenOrderByLoanDateAsc(LocalDate start, LocalDate end);
    boolean existsByCustodianIdAndLoanDateAndStatusNot(UUID custodianId, LocalDate loanDate, LoanStatus status);
}