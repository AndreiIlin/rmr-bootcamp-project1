package com.truestore.backend.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReportRepositoryImpl implements ReportRepository {
    private final JpaReportRepository jpaReportRepository;

    @Autowired
    public ReportRepositoryImpl(JpaReportRepository jpaReportRepository) {
        this.jpaReportRepository = jpaReportRepository;
    }

    @Override
    public Optional<Report> saveReport(Report report) {
        try {
            return Optional.of(jpaReportRepository.save(report));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
