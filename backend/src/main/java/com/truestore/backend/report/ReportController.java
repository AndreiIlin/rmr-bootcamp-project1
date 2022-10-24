package com.truestore.backend.report;

import com.truestore.backend.report.dto.CreateReportDto;
import com.truestore.backend.report.dto.ReportDto;
import com.truestore.backend.report.dto.ShortReportDto;
import com.truestore.backend.report.dto.UpdateReportDto;
import com.truestore.backend.security.SecurityUser;
import com.truestore.backend.user.User;
import com.truestore.backend.validation.ValidationErrorBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create Report (feature, bug, claim) to the Contract from Current User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Report is created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateReportDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Only contract QA can sent report",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save report",
                    content = @Content)})
    @PostMapping("/reports/{reportType}")
    public ResponseEntity<?> createReport(
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

    @Operation(summary = "Update Report (feature, bug, claim) from Current User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Report is updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateReportDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Only contract QA can update report",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find report",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to update report",
                    content = @Content)})
    @PatchMapping("/reports")
    public ResponseEntity<?> updateReport(
            HttpServletRequest request,
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

    @Operation(summary = "Get list of Short Reports for current User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ShortReportDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content)})
    @GetMapping("/reports/my")
    public ResponseEntity<?> getReportsForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return new ResponseEntity<>(reportService.getReportsForCurrentUser(user).stream()
                        .map(this::convertToShortDto), HttpStatus.OK);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Get list of Short Reports for current User in Contract")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ShortReportDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Only contract QA can get reports",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find contract",
                    content = @Content)})
    @GetMapping("/reports/contract/{contractId}/my")
    public ResponseEntity<?> getReportsInContractForCurrentUser(@PathVariable UUID contractId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return new ResponseEntity<>(reportService.getReportsInContractForCurrentUser(contractId, user).stream()
                        .map(this::convertToShortDto), HttpStatus.OK);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Get list of Short Reports for current App Owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ShortReportDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Only App owner can get reports",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find App",
                    content = @Content)})
    @GetMapping("/reports/app/{appId}")
    public ResponseEntity<?> getReportsForAppByOwner(@PathVariable UUID appId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return new ResponseEntity<>(reportService.getReportsForAppByOwner(appId, user).stream()
                        .map(this::convertToShortDto), HttpStatus.OK);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Get Report by Id with Current User (QA or Owner)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReportDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Only App owner or owner can get report",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find Report",
                    content = @Content)})
    @GetMapping("/reports/{reportId}")
    public ResponseEntity<?> getReportById(@PathVariable UUID reportId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return new ResponseEntity<>(convertToDto(reportService.getReportById(reportId, user)), HttpStatus.OK);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Approve reports (feature, bug) by App Owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReportDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Only App owner can approve waiting report",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find Report",
                    content = @Content)})
    @PatchMapping("/reports/{reportId}/approve")
    public ResponseEntity<?> approveReportById(@PathVariable UUID reportId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return new ResponseEntity<>(convertToDto(reportService.approveReportById(reportId, user)), HttpStatus.OK);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Reject reports (feature, bug) by App Owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReportDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Only App owner can reject waiting report",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find Report",
                    content = @Content)})
    @PatchMapping("/reports/{reportId}/reject")
    public ResponseEntity<?> rejectReportById(@PathVariable UUID reportId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return new ResponseEntity<>(convertToDto(reportService.rejectReportById(reportId, user)), HttpStatus.OK);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    private ReportDto convertToDto(Report report) {
        return modelMapper.map(report, ReportDto.class);
    }

    private ShortReportDto convertToShortDto(Report report) {
        return modelMapper.map(report, ShortReportDto.class);
    }
}
