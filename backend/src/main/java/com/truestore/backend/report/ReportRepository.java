package com.truestore.backend.report;


import java.util.Optional;
import java.util.UUID;

public interface ReportRepository {

    Optional<Report> saveReport(Report report);

    Optional<Report> getReportById(UUID reportId);
}
