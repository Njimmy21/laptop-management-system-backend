package com.example.laptop_management_system.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.laptop_management_system.domain.Loan;
import com.example.laptop_management_system.domain.LoanStatus;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
    List<Loan> findByStatusOrderByLoanDateAsc(LoanStatus status);
    List<Loan> findByLoanDateBetweenOrderByLoanDateAsc(LocalDate start, LocalDate end);
    boolean existsByCustodianIdAndLoanDateAndStatusNot(UUID custodianId, LocalDate loanDate, LoanStatus status);

    @Query("SELECT l.status, COUNT(l) FROM Loan l GROUP BY l.status")
    List<Object[]> countLoansByStatus();
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Loan l SET l.status = :refused " +
        "WHERE l.custodian.id = :custodianId AND l.loanDate = :date AND l.status <> :returned")
    int cancelLoansOfDay(@Param("custodianId") UUID custodianId,
                        @Param("date") LocalDate date,
                        @Param("refused") LoanStatus refused,
                        @Param("returned") LoanStatus returned);
}