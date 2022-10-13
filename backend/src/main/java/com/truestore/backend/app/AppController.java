package com.truestore.backend.app;


import com.truestore.backend.app.dto.AppDto;
import com.truestore.backend.app.dto.CreateAppDto;
import com.truestore.backend.security.SecurityUser;
import com.truestore.backend.user.User;
import com.truestore.backend.validation.ValidationErrorBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping(value = AppController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AppController {

    static final String REST_URL = "/apps";
    static final String WRONG_CREDENTIALS = "Wrong credentials";

    private final AppService appService;
    private final ModelMapper modelMapper = new ModelMapper();

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @Operation(summary = "Get all apps with pagination and available filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got all Apps with pagination and available filter",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AppDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content) })
    @GetMapping
    public ResponseEntity<?> getAllApps(
            @Parameter(description = "Zero-based page index, must not be negative")
            @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "The size of the page to be returned, must be greater than 0")
            @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(description = "Use a parameter filter to show records with the same parameter value")
            @RequestParam(required = false, defaultValue = "") String filter) {
        log.info("get all Apps");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            List<AppDto> appsDto = appService.getAllAppsUsingFilters(filter,
                            PageRequest.of(page, size, Sort.by("created").descending()))
                    .stream().map(app -> convertToDto(AppDto.class, app)).collect(Collectors.toList());
            return ResponseEntity.ok(appsDto);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Get all apps of current user with pagination and available filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got all apps of current user with pagination and available filter",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AppDto.class)))}),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content) })
    @GetMapping("/my")
    public ResponseEntity<?> getAllMyAppsWithFilters(
            @Parameter(description = "Zero-based page index, must not be negative")
            @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "The size of the page to be returned, must be greater than 0")
            @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(description = "Use a parameter filter to show records with the same parameter value")
            @RequestParam(required = false, defaultValue = "") String filter) {
        log.info("get all my Apps");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                List<AppDto> appsDto = appService.getAllMyAppsUsingFilters(user.getId(), filter,
                                PageRequest.of(page, size, Sort.by("created").descending()))
                        .stream().map(app -> convertToDto(AppDto.class, app)).collect(Collectors.toList());
                return ResponseEntity.ok(appsDto);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Get information about app by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the app by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "App not found by id",
                    content = @Content)})
    @GetMapping("/{appId}")
    public ResponseEntity<?> getAppById(@Parameter(description = "Id App") @PathVariable String appId) {
        log.info("get App by id {}", appId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return ResponseEntity.ok(convertToDto(AppDto.class, appService.getAppById(appId)));
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Create new app by current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the app by current user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content)})
    @PostMapping
    @Validated
    public ResponseEntity<?> createApp(HttpServletRequest request, @Valid @RequestBody CreateAppDto createAppDto, Errors errors) {
        log.info("create App {}", createAppDto);
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                App app = convertToEntity(createAppDto);
                return new ResponseEntity<>(convertToDto(AppDto.class, appService.saveAppForUser(app, user)), HttpStatus.CREATED);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Delete app by id for current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the app by id for the current user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "App not found",
                    content = @Content) })
    @DeleteMapping("/{appId}")
    public ResponseEntity<?> deleteAppById(@Parameter(description = "Id App") @PathVariable String appId) {
        log.info("delete App by id {}", appId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User owner = appService.getAppById(appId).getOwner();
                User user = ((SecurityUser) principal).getUser();
                if (!Objects.equals(owner.getId(), user.getId())) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No permissions");
                }
                return  ResponseEntity.ok(convertToDto(AppDto.class, appService.deleteAppById(appId)));
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    @Operation(summary = "Update app by id for current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated app by id for current user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "App not found",
                    content = @Content) })
    @PatchMapping("/{appId}")
    @Validated
    public ResponseEntity<?> updateApp(HttpServletRequest request, @RequestBody @Valid AppDto appDto,
                                    @PathVariable String appId, Errors errors) {
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        log.info("delete App by id {}", appId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User owner = appService.getAppById(appId).getOwner();
                User user = ((SecurityUser) principal).getUser();
                if (!Objects.equals(owner.getId(), user.getId())) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No permissions");
                }
                App app = convertToEntity(appDto);
                return ResponseEntity.ok(convertToDto(AppDto.class, appService.saveAppForUser(app, user)));
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    private <T> T convertToDto(Class<T> clazz, App app) {
        return modelMapper.map(app, clazz);
    }

    private <T> App convertToEntity(T dto) {
        return modelMapper.map(dto, App.class);
    }
}
