package com.truestore.backend.report;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReportRepository extends JpaRepository<Report, String> {
}
