package com.truestore.backend.report;


import java.util.Optional;

public interface ReportRepository {

    Optional<Report> saveReport(Report report);
}
