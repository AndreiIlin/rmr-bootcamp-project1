package com.truestore.backend.contract;

import com.truestore.backend.contract.dto.ContractDto;
import com.truestore.backend.contract.dto.CreateContractDto;
import com.truestore.backend.security.SecurityUser;
import com.truestore.backend.user.User;
import com.truestore.backend.validation.ValidationErrorBuilder;
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

    private ContractDto convertToDto(Contract contract) {
        return modelMapper.map(contract, ContractDto.class);
    }
}
