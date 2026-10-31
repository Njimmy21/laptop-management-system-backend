package com.example.laptop_management_system.repository;

import com.example.laptop_management_system.domain.ConditionReport;
import com.example.laptop_management_system.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ConditionReportRepository extends JpaRepository<ConditionReport, UUID> {
    @Query("SELECT d FROM ConditionReport d " +
           "WHERE d.loan.student.studentNumber = :studentNumber " +
           "ORDER BY d.inspectedDate DESC")
    List<ConditionReport> findHistoryOfStudent(@Param("studentNumber") String studentNumber);

    boolean existsByLoan(Loan loan);
}