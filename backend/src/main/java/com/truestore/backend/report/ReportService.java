package com.truestore.backend.report;

import com.truestore.backend.app.App;
import com.truestore.backend.app.AppRepository;
import com.truestore.backend.contract.Contract;
import com.truestore.backend.contract.ContractRepository;
import com.truestore.backend.report.dto.CreateReportDto;
import com.truestore.backend.report.dto.UpdateReportDto;
import com.truestore.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final ContractRepository contractRepository;
    private final AppRepository appRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository, ContractRepository contractRepository, AppRepository appRepository) {
        this.reportRepository = reportRepository;
        this.contractRepository = contractRepository;
        this.appRepository = appRepository;
    }

    private Report createReportForUser(CreateReportDto createReportDto, ReportType reportType, User user) {
        Contract contract = contractRepository.getContractById(createReportDto.getContractId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find contract")
        );
        if (!contract.getQa().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only contract QA can sent report");
        }
        Report report = new Report();
        report.setTitle(createReportDto.getTitle());
        report.setDescription(createReportDto.getDescription());
        report.setContract(contract);
        report.setReportType(reportType);
        report.setReportStatus(ReportStatus.WAITING);
        return reportRepository.saveReport(report).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.CONFLICT, "Unable to save report")
        );
    }

    public Report createBugReport(CreateReportDto createReportDto, User user) {
        return createReportForUser(createReportDto, ReportType.BUG, user);
    }

    public Report createFeatureReport(CreateReportDto createReportDto, User user) {
        return createReportForUser(createReportDto, ReportType.FEATURE, user);
    }

    public Report createClaimReport(CreateReportDto createReportDto, User user) {
        return createReportForUser(createReportDto, ReportType.CLAIM, user);
    }

    public Report updateReportForUser(UpdateReportDto updateReportDto, User user) {
        Report report = reportRepository.getReportById(updateReportDto.getReportId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find report")
        );
        if (!report.getContract().getQa().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only report QA can sent report");
        }
        if (updateReportDto.getTitle() != null) report.setTitle(updateReportDto.getTitle());
        if (updateReportDto.getDescription() != null) report.setDescription(updateReportDto.getDescription());
        return reportRepository.saveReport(report).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.CONFLICT, "Unable to save report")
        );
    }

    public List<Report> getReportsForCurrentUser(User user) {
        return reportRepository.getReportsForUser(user);
    }

    public List<Report> getReportsInContractForCurrentUser(UUID contractId, User user) {
        Contract contract = contractRepository.getContractById(contractId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find contract")
        );
        if (!contract.getQa().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can get only own contracts");
        }
        return reportRepository.getReportsInContractForUser(contract, user);
    }

    public List<Report> getReportsForAppByOwner(UUID appId, User user) {
        App app = appRepository.getAppById(appId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find app")
        );
        if (!app.getOwner().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only app owner can get reports");
        }
        return reportRepository.getReportsForAppByOwner(app);
    }
}
