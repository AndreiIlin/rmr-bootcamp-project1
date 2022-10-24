package com.truestore.backend.contract;

import com.truestore.backend.app.App;
import com.truestore.backend.app.dto.ShortAppDto;
import com.truestore.backend.contract.dto.ContractDto;
import com.truestore.backend.contract.dto.CreateContractDto;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping(value = ContractController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ContractController {
    static final String REST_URL = "/contracts";
    static final String WRONG_CREDENTIALS = "Wrong credentials";
    private final ContractService contractService;
    private final ModelMapper modelMapper;

    @Autowired
    public ContractController(ContractService contractService, ModelMapper modelMapper) {
        this.contractService = contractService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Create contract for current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contract created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContractDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "App owner can't contract an own app",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find app",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save contract",
                    content = @Content)})
    @PostMapping("")
    public ResponseEntity<?> createContract(
            HttpServletRequest request,
            @RequestBody @Valid CreateContractDto createContractDto,
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
                        convertToDto(contractService.createContractForUser(createContractDto.getAppId(), user)),
                        HttpStatus.CREATED
                );
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Get list of Contracts for current User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ContractDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content)})
    @GetMapping("/my")
    public ResponseEntity<?> getContractsForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return ResponseEntity.ok(contractService.getContractsForUser(user).stream()
                        .map(this::convertToDto).collect(Collectors.toList()));
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Get Contract by UUID with current User credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get contract",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContractDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User can see only own contracts",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unable to find contract with such UUID",
                    content = @Content)})
    @GetMapping("/{contractId}")
    public ResponseEntity<?> getContractById(@PathVariable UUID contractId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return ResponseEntity.ok(convertToDto(contractService.getContractById(contractId, user)));
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Get list of contracted Apps for current User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ShortAppDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content)})
    @GetMapping("/apps/my")
    public ResponseEntity<?> getContractedAppsForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return ResponseEntity.ok(contractService.getContractedAppsForCurrentUser(user).stream()
                        .map(this::convertToShortAppDto).collect(Collectors.toList()));
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    private ContractDto convertToDto(Contract contract) {
        return modelMapper.map(contract, ContractDto.class);
    }

    private ShortAppDto convertToShortAppDto(App app) {
        return modelMapper.map(app, ShortAppDto.class);
    }
}
