package com.example.laptop_management_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.laptop_management_system.domain.Loan;
import com.example.laptop_management_system.domain.LoanStatus;
import com.example.laptop_management_system.repository.LoanRepository;

import com.example.laptop_management_system.repository.ConditionReportRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final ConditionReportRepository conditionReportRepository;

    public LoanService(LoanRepository loanRepository, ConditionReportRepository conditionReportRepository) {
        this.loanRepository = loanRepository;
        this.conditionReportRepository = conditionReportRepository;
    }

    public String save(Loan loan) {
        if (loan.getLoanDate().isBefore(LocalDate.now())) {
            return "Loan date cannot be in the past";
        }
        boolean busy = loanRepository.existsByCustodianIdAndLoanDateAndStatusNot(
                loan.getCustodian().getId(), loan.getLoanDate(), LoanStatus.REFUSED);
        if (busy) {
            return "Custodian already has a loan on that date";
        }
        loan.setStatus(LoanStatus.REQUESTED);
        loanRepository.save(loan);
        return "Loan saved successfully";
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Optional<Loan> findById(UUID id) {
        return loanRepository.findById(id);
    }

    public String update(UUID id, Loan loan) {
        Optional<Loan> existing = loanRepository.findById(id);
        if (existing.isEmpty()) {
            return "The loan with that id does not exist";
        }
        Loan toUpdate = existing.get();
        toUpdate.setLoanDate(loan.getLoanDate());
        toUpdate.setSerialNumber(loan.getSerialNumber());
        toUpdate.setDaysRequested(loan.getDaysRequested());
        loanRepository.save(toUpdate);
        return "Loan updated successfully";
    }

    public String delete(UUID id) {
        Optional<Loan> loan = loanRepository.findById(id);
        if (loan.isEmpty()) {
            return "The loan with that id does not exist";
        }
        if (conditionReportRepository.existsByLoan(loan.get())) {
            return "That loan has condition reports, delete them first";
        }
        loanRepository.deleteById(id);
        return "Loan deleted successfully";
    }

    public List<Loan> findByStatus(LoanStatus status) {
        return loanRepository.findByStatusOrderByLoanDateAsc(status);
    }

    public List<Loan> findBetween(LocalDate start, LocalDate end) {
        return loanRepository.findByLoanDateBetweenOrderByLoanDateAsc(start, end);
    }

    public String updateStatus(UUID id, LoanStatus newStatus) {
        Optional<Loan> existing = loanRepository.findById(id);
        if (existing.isEmpty()) {
            return "The loan with that id does not exist";
        }
        Loan loan = existing.get();
        LoanStatus current = loan.getStatus();

        boolean allowed = false;
        if (current == LoanStatus.REQUESTED) {
            allowed = newStatus == LoanStatus.ISSUED || newStatus == LoanStatus.REFUSED;
        } else if (current == LoanStatus.ISSUED) {
            allowed = newStatus == LoanStatus.RETURNED || newStatus == LoanStatus.REFUSED;
        }

        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        loan.setStatus(newStatus);
        loanRepository.save(loan);
        return "Loan status updated successfully";
    }

    public List<Object[]> countLoansByStatus() {
        return loanRepository.countLoansByStatus();
    }

    @Transactional
    public String cancelDay(UUID custodianId, LocalDate date) {
        int count = loanRepository.cancelLoansOfDay(custodianId, date, LoanStatus.REFUSED, LoanStatus.RETURNED);
        return count + " loans cancelled";
    }
}