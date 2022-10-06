package com.truestore.backend.app;


import com.truestore.backend.security.SecurityUser;
import com.truestore.backend.user.User;
import com.truestore.backend.validation.OnUpdate;
import com.truestore.backend.validation.ValidationErrorBuilder;
import com.truestore.backend.validation.user.OnCreate;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping(value = AppController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AppController {

    static final String REST_URL = "/apps";

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @Operation(summary = "Get all Apps with pagination and available filter")
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "created") String order,
            @RequestParam(required = false, defaultValue = "") String filter) {
        log.info("get all Apps");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return ResponseEntity.ok(appService.getAll(filter, PageRequest.of(pageNumber, pageSize, Sort.by(order).descending())));
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong credentials");
    }

    @Operation(summary = "Get all my Apps with pagination and available filter")
    @GetMapping("/my")
    public ResponseEntity<?> getAllMy(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "") String filter) {
        log.info("get all my Apps");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return ResponseEntity.ok(appService.getAllMy(user.getId(), filter, PageRequest.of(pageNumber, pageSize)));
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong credentials");
    }

    @Operation(summary = "Get information about App by id")
    @GetMapping("/{appId}")
    public ResponseEntity<?> getById(@PathVariable String appId) {
        log.info("get App by id {}", appId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return ResponseEntity.ok(appService.getAppTo(appId));
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong credentials");
    }

    @Operation(summary = "Create new App by current User")
    @PostMapping
    @Validated(OnCreate.class)
    public ResponseEntity<?> create(HttpServletRequest request, @Valid @RequestBody App app, Errors errors) {
        log.info("create App {}", app);
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return new ResponseEntity<>(appService.save(app, user), HttpStatus.CREATED);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong credentials");
    }

    @Operation(summary = "Delete App by id by app owner")
    @DeleteMapping("/{appId}")
    public ResponseEntity<?> deleteById(@PathVariable String appId) {
        log.info("delete App by id {}", appId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                String ownerId = appService.getAppTo(appId).getOwnerId();
                User user = ((SecurityUser) principal).getUser();
                if (!Objects.equals(ownerId, user.getId())) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No permissions");
                }
                return  ResponseEntity.ok(appService.delete(appId));
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong credentials");
    }

    @Operation(summary = "Update App by id by app owner")
    @PatchMapping("/{appId}")
    @Validated(OnUpdate.class)
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody @Valid App app,
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
                String ownerId = appService.getAppTo(appId).getOwnerId();
                User user = ((SecurityUser) principal).getUser();
                if (!Objects.equals(ownerId, user.getId())) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No permissions");
                }
                return  ResponseEntity.ok(appService.save(app, user));
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong credentials");
    }
}
