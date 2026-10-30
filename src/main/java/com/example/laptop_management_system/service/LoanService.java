package com.example.laptop_management_system.service;

import com.example.laptop_management_system.domain.Loan;
import com.example.laptop_management_system.domain.LoanStatus;
import com.example.laptop_management_system.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public String save(Loan loan) {
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
        if (!loanRepository.existsById(id)) {
            return "The loan with that id does not exist";
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
}