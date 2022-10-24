package com.truestore.backend.report;

import com.truestore.backend.report.dto.CreateReportDto;
import com.truestore.backend.report.dto.ReportDto;
import com.truestore.backend.report.dto.UpdateReportDto;
import com.truestore.backend.security.SecurityUser;
import com.truestore.backend.user.User;
import com.truestore.backend.validation.ValidationErrorBuilder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.UUID;

@RestController
@Slf4j
@Validated
public class ReportController {
    static final String WRONG_CREDENTIALS = "Wrong credentials";
    private final ReportService reportService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReportController(ReportService reportService, ModelMapper modelMapper) {
        this.reportService = reportService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/reports/{reportType}")
    public ResponseEntity<?> createBugReport(
            HttpServletRequest request,
            @PathVariable String reportType,
            @RequestBody @Valid CreateReportDto createReportDto,
            Errors errors) {
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        String[] reportTypes = {"bug", "feature", "claim"};
        if (!Arrays.asList(reportTypes).contains(reportType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find such report type");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                ResponseEntity<Object> result;
                switch (reportType) {
                    case "bug":
                        result = new ResponseEntity<>(
                                convertToDto(reportService.createBugReport(createReportDto, user)),
                                HttpStatus.CREATED);
                        break;
                    case "claim":
                        result = new ResponseEntity<>(
                                convertToDto(reportService.createClaimReport(createReportDto, user)),
                                HttpStatus.CREATED);
                        break;
                    case "feature":
                        result = new ResponseEntity<>(
                                convertToDto(reportService.createFeatureReport(createReportDto, user)),
                                HttpStatus.CREATED
                        );
                        break;
                    default:
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find such report type");
                }
                return result;
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @PatchMapping("/reports")
    public ResponseEntity<?> updateBugReport(
            HttpServletRequest request,
            @PathVariable String reportType,
            @RequestBody @Valid UpdateReportDto updateReportDto,
            Errors errors) {
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return new ResponseEntity<>(
                        convertToDto(reportService.updateReportForUser(updateReportDto, user)),
                        HttpStatus.OK
                );
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @GetMapping("reports/my")
    public ResponseEntity<?> getReportsForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return new ResponseEntity<>(reportService.getReportsForCurrentUser(user).stream()
                        .map(this::convertToDto), HttpStatus.OK);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @GetMapping("reports/contract/{contractId}/my")
    public ResponseEntity<?> getReportsInContractForCurrentUser(@PathVariable UUID contractId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return new ResponseEntity<>(reportService.getReportsInContractForCurrentUser(contractId, user).stream()
                        .map(this::convertToDto), HttpStatus.OK);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    private ReportDto convertToDto(Report report) {
        return modelMapper.map(report, ReportDto.class);
    }
}
