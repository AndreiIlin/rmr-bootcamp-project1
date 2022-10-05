package com.truestore.backend.user;

import com.truestore.backend.security.SecurityUser;
import com.truestore.backend.validation.ValidationErrorBuilder;
import com.truestore.backend.validation.user.OnCreate;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserController {

    static final String REST_URL = "/users";
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Operation(summary = "Login user with email and password to obtain JWT access token")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(HttpServletRequest request, @Valid @RequestBody LoginRequest loginRequest, Errors errors) {
        log.info("authenticate {}", loginRequest);
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong credentials");
        }
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @Operation(summary = "Sign up new user to work with API")
    @PostMapping("/signup")
    @Validated(OnCreate.class)
    public ResponseEntity<?> registerUser(HttpServletRequest request, @RequestBody @Valid LoginRequest loginRequest, Errors errors) {
        log.info("register {}", loginRequest);
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.unprocessableEntity().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        try {
            UserRole.valueOf(loginRequest.getRole());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not found role");
        }
        return new ResponseEntity<>(userService.signup(loginRequest), HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return ResponseEntity.ok(new UserTo(user.getEmail(), user.getId()));
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong credentials");
    }

}
