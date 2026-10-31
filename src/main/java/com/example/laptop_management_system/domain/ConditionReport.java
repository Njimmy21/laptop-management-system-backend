package com.example.laptop_management_system.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "condition_report")
public class ConditionReport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String issue;

    private String remarks;

    private int penaltyAmount;

    private LocalDate inspectedDate;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    public ConditionReport() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(int penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public LocalDate getInspectedDate() {
        return inspectedDate;
    }

    public void setInspectedDate(LocalDate inspectedDate) {
        this.inspectedDate = inspectedDate;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}