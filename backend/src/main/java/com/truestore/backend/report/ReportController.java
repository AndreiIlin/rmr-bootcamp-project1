package com.truestore.backend.report;

import com.truestore.backend.report.dto.CreateReportDto;
import com.truestore.backend.report.dto.ReportDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @PostMapping("/reports/bug")
    public ResponseEntity<?> createBugReport(
            HttpServletRequest request,
            @RequestBody @Valid CreateReportDto createReportDto,
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
                        convertToDto(reportService.createBugReport(createReportDto, user)),
                        HttpStatus.CREATED
                );
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    private ReportDto convertToDto(Report report) {
        return modelMapper.map(report, ReportDto.class);
    }
}
