package com.truestore.backend.report;


import com.truestore.backend.contract.Contract;
import com.truestore.backend.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReportRepository {

    Optional<Report> saveReport(Report report);

    Optional<Report> getReportById(UUID reportId);

    List<Report> getReportsForUser(User user);

    List<Report> getReportsInContractForUser(Contract contract, User user);
}
