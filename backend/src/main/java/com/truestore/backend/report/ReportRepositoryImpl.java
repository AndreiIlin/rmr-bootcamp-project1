package com.truestore.backend.report;

import com.truestore.backend.contract.Contract;
import com.truestore.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<Report> getReportById(UUID reportId) {
        return jpaReportRepository.findById(String.valueOf(reportId));
    }

    @Override
    public List<Report> getReportsForUser(User user) {
        return jpaReportRepository.findAllByContractQaOrderByCreatedDesc(user);
    }

    @Override
    public List<Report> getReportsInContractForUser(Contract contract, User user) {
        return jpaReportRepository.findAllByContractQaAndContractOrderByCreatedDesc(user, contract);
    }
}
