package com.truestore.backend.report;

import com.truestore.backend.app.App;
import com.truestore.backend.app.AppRepository;
import com.truestore.backend.contract.Contract;
import com.truestore.backend.contract.ContractRepository;
import com.truestore.backend.report.dto.CreateReportDto;
import com.truestore.backend.report.dto.UpdateReportDto;
import com.truestore.backend.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
    private static final UUID UUID_1 = UUID.randomUUID();
    private static final User USER_1 = new User(String.valueOf(UUID_1),
            "mail@mail.ru", "password", "ROLE_USER");
    private static final UUID UUID_2 = UUID.randomUUID();
    private static final User USER_2 = new User(String.valueOf(UUID_2),
            "second@mail.ru", "password", "ROLE_USER");
    private static final UUID UUID_3 = UUID.randomUUID();
    private static final App APP_1 = new App(
            String.valueOf(UUID_3),
            "App1", "AppDesc1", USER_1, 1F, 1F, true,
            "image", "download", LocalDateTime.now(), null);
    private static final UUID UUID_4 = UUID.randomUUID();
    private static final Contract CONTRACT_1 = new Contract(String.valueOf(UUID_4), APP_1, USER_2, LocalDateTime.now());
    private static final UUID UUID_5 = UUID.randomUUID();
    private static final Report REPORT_1 = new Report(
            String.valueOf(UUID_5), CONTRACT_1, "title1", "desc1", ReportType.FEATURE,
            ReportStatus.WAITING, LocalDateTime.now());
    @InjectMocks
    private ReportService reportService;
    @Mock
    private ReportRepository reportRepository;
    @Mock
    private ContractRepository contractRepository;
    @Mock
    private AppRepository appRepository;

    @Test
    void createBugReport() {
        when(contractRepository.getContractById(any(UUID.class))).thenReturn(Optional.of(CONTRACT_1));
        when(reportRepository.saveReport(any(Report.class))).thenReturn(Optional.of(REPORT_1));
        Report report = reportService.createBugReport(
                new CreateReportDto(UUID_4, "title1", "desc1"), USER_2);
        assertEquals(REPORT_1.getId(), report.getId());
    }

    @Test
    void createFeatureReport() {
        when(contractRepository.getContractById(any(UUID.class))).thenReturn(Optional.of(CONTRACT_1));
        when(reportRepository.saveReport(any(Report.class))).thenReturn(Optional.of(REPORT_1));
        Report report = reportService.createFeatureReport(
                new CreateReportDto(UUID_4, "title1", "desc1"), USER_2);
        assertEquals(REPORT_1.getId(), report.getId());
    }

    @Test
    void createClaimReport() {
        when(contractRepository.getContractById(any(UUID.class))).thenReturn(Optional.of(CONTRACT_1));
        when(reportRepository.saveReport(any(Report.class))).thenReturn(Optional.of(REPORT_1));
        Report report = reportService.createClaimReport(
                new CreateReportDto(UUID_4, "title1", "desc1"), USER_2);
        assertEquals(REPORT_1.getId(), report.getId());
    }

    @Test
    void updateReportForUser() {
        when(reportRepository.getReportById(any(UUID.class))).thenReturn(Optional.of(REPORT_1));
        when(reportRepository.saveReport(any(Report.class))).thenReturn(Optional.of(REPORT_1));
        Report report = reportService.updateReportForUser(
                new UpdateReportDto(UUID_5, "titleU", "descU"), USER_2);
        assertEquals(REPORT_1.getId(), report.getId());
    }

    @Test
    void getReportsForCurrentUser() {
        when(reportRepository.getReportsForUser(any(User.class))).thenReturn(Collections.singletonList(REPORT_1));
        List<Report> reports = reportService.getReportsForCurrentUser(USER_2);
        assertEquals(REPORT_1.getId(), reports.get(0).getId());
    }

    @Test
    void getReportsInContractForCurrentUser() {
        when(contractRepository.getContractById(any(UUID.class))).thenReturn(Optional.of(CONTRACT_1));
        when(reportRepository.getReportsInContractForUser(
                any(Contract.class), any(User.class))).thenReturn(Collections.singletonList(REPORT_1));
        List<Report> reports = reportService.getReportsInContractForCurrentUser(UUID_4, USER_2);
        assertEquals(REPORT_1.getId(), reports.get(0).getId());
    }

    @Test
    void getReportsForAppByOwner() {
        when(appRepository.getAppById(any(UUID.class))).thenReturn(Optional.of(APP_1));
        when(reportRepository.getReportsForAppByOwner(any(App.class))).thenReturn(Collections.singletonList(REPORT_1));
        List<Report> reports = reportService.getReportsForAppByOwner(UUID_3, USER_1);
        assertEquals(REPORT_1.getId(), reports.get(0).getId());
    }

    @Test
    void getReportById() {
        when(reportRepository.getReportById(any(UUID.class))).thenReturn(Optional.of(REPORT_1));
        Report report = reportService.getReportById(UUID_5, USER_1);
        assertEquals(REPORT_1.getId(), report.getId());
    }

    @Test
    void approveReportById() {
        REPORT_1.setReportStatus(ReportStatus.WAITING);
        when(reportRepository.getReportById(any(UUID.class))).thenReturn(Optional.of(REPORT_1));
        when(reportRepository.saveReport(any(Report.class))).thenReturn(Optional.of(REPORT_1));
        Report report = reportService.approveReportById(UUID_5, USER_1);
        assertEquals(REPORT_1.getId(), report.getId());
        assertEquals(ReportStatus.APPROVED, report.getReportStatus());
    }

    @Test
    void rejectReportById() {
        REPORT_1.setReportStatus(ReportStatus.WAITING);
        when(reportRepository.getReportById(any(UUID.class))).thenReturn(Optional.of(REPORT_1));
        when(reportRepository.saveReport(any(Report.class))).thenReturn(Optional.of(REPORT_1));
        Report report = reportService.rejectReportById(UUID_5, USER_1);
        assertEquals(REPORT_1.getId(), report.getId());
        assertEquals(ReportStatus.REJECTED, report.getReportStatus());
    }
}