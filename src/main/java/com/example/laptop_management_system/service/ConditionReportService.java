package com.example.laptop_management_system.service;

import com.example.laptop_management_system.domain.ConditionReport;
import com.example.laptop_management_system.domain.Loan;
import com.example.laptop_management_system.domain.LoanStatus;
import com.example.laptop_management_system.repository.ConditionReportRepository;
import com.example.laptop_management_system.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConditionReportService {

    private final ConditionReportRepository conditionReportRepository;
    private final LoanRepository loanRepository;

    public ConditionReportService(ConditionReportRepository conditionReportRepository,
                                  LoanRepository loanRepository) {
        this.conditionReportRepository = conditionReportRepository;
        this.loanRepository = loanRepository;
    }
    
    public String save(UUID loanId, ConditionReport report) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isEmpty()) {
            return "The loan with that id does not exist";
        }
        if (loan.get().getStatus() != LoanStatus.RETURNED) {
            return "Condition reports are only for RETURNED loans";
        }
        report.setId(null);
        report.setLoan(loan.get());
        report.setInspectedDate(LocalDate.now());
        conditionReportRepository.save(report);
        return "Condition report saved successfully";
    }

    public List<ConditionReport> findAll() {
        return conditionReportRepository.findAll();
    }

    public List<ConditionReport> findHistoryOfStudent(String studentNumber) {
        return conditionReportRepository.findHistoryOfStudent(studentNumber);
    }

    public String delete(UUID id) {
        if (!conditionReportRepository.existsById(id)) {
            return "The condition report with that id does not exist";
        }
        conditionReportRepository.deleteById(id);
        return "Condition report deleted successfully";
    }
}