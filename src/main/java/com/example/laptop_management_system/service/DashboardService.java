package com.example.laptop_management_system.service;

import com.example.laptop_management_system.domain.LoanStatus;
import com.example.laptop_management_system.repository.ConditionReportRepository;
import com.example.laptop_management_system.repository.CustodianRepository;
import com.example.laptop_management_system.repository.LoanRepository;
import com.example.laptop_management_system.repository.StoreRoomRepository;
import com.example.laptop_management_system.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final StudentRepository studentRepository;
    private final CustodianRepository custodianRepository;
    private final StoreRoomRepository storeRoomRepository;
    private final LoanRepository loanRepository;
    private final ConditionReportRepository conditionReportRepository;

    public DashboardService(StudentRepository studentRepository,
                            CustodianRepository custodianRepository,
                            StoreRoomRepository storeRoomRepository,
                            LoanRepository loanRepository,
                            ConditionReportRepository conditionReportRepository) {
        this.studentRepository = studentRepository;
        this.custodianRepository = custodianRepository;
        this.storeRoomRepository = storeRoomRepository;
        this.loanRepository = loanRepository;
        this.conditionReportRepository = conditionReportRepository;
    }

    public Map<String, Long> summary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("students", studentRepository.count());
        summary.put("custodians", custodianRepository.count());
        summary.put("freeStoreRooms", (long) storeRoomRepository.findFreeStoreRooms().size());
        summary.put("loansToday", loanRepository.countByLoanDate(LocalDate.now()));
        summary.put("requested", loanRepository.countByStatus(LoanStatus.REQUESTED));
        summary.put("conditionReports", conditionReportRepository.count());
        return summary;
    }
}