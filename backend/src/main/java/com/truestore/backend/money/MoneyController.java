package com.truestore.backend.money;

import com.truestore.backend.money.dto.CreateMoneyDto;
import com.truestore.backend.money.dto.MoneyDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping(value = MoneyController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class MoneyController {

    static final String REST_URL = "/money";
    static final String WRONG_CREDENTIALS = "Wrong credentials";
    private final MoneyService moneyService;
    private final ModelMapper modelMapper;

    public MoneyController(MoneyService moneyService, ModelMapper modelMapper) {
        this.moneyService = moneyService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Top up the balance for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Balance is replenished",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MoneyDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "The user can only top up his own balance",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save transaction",
                    content = @Content)})
    @PostMapping("/replenishment")
    public ResponseEntity<?> replenishmentBalanceForCurrentUser(
            HttpServletRequest request,
            @RequestBody @Valid CreateMoneyDto createMoneyDto,
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
                        convertToDto(moneyService.replenishmentBalanceForUser(createMoneyDto, user)),
                        HttpStatus.CREATED
                );
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Withdrawal for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Withdrawal completed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MoneyDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "The user can only withdraw from his own balance",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save transaction",
                    content = @Content)})
    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawalBalanceForCurrentUser(
            HttpServletRequest request,
            @RequestBody @Valid CreateMoneyDto createMoneyDto,
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
                        convertToDto(moneyService.withdrawalBalanceForUser(createMoneyDto, user)),
                        HttpStatus.CREATED
                );
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Get list of Money Transition for current User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MoneyDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content)})
    @GetMapping("/my")
    public ResponseEntity<?> getMoneyTransitionsForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return ResponseEntity.ok(moneyService.getMoneyTransitionsForUser(user).stream()
                        .map(this::convertToDto).collect(Collectors.toList()));
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);

    }

    @Operation(summary = "Get balance for current User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "text/html",
                            schema = @Schema(implementation = Float.class))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content)})
    @GetMapping("/balance")
    public ResponseEntity<?> getBalanceForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return ResponseEntity.ok(moneyService.getBalanceByUser(user));
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    private MoneyDto convertToDto(Money money) {
        return modelMapper.map(money, MoneyDto.class);
    }

}
