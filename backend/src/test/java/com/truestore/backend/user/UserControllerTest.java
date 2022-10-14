package com.truestore.backend.user;

import com.truestore.backend.AbstractControllerTest;
import com.truestore.backend.security.JWTToken;
import com.truestore.backend.user.dto.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithAnonymousUser;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Set;

import static com.truestore.backend.user.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractControllerTest {
    private static final String REST_URL = UserController.REST_URL + '/';
    @MockBean
    private UserService userService;
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private Validator validator;

    @Test
    @WithAnonymousUser
    void signupUser() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail(USER_1_MAIL);
        request.setPassword(USER_PASSWORD);
        request.setRole(UserRole.ROLE_USER.toString());
        JWTToken token = new JWTToken(USER_1_UUID, VALID_TOKEN);
        when(userService.signup(any(LoginRequest.class))).thenReturn(token);
        perform(post(REST_URL + "signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.access_token").value(VALID_TOKEN));
    }

    @Test
    @WithAnonymousUser
    void signupUserInvalidNullEmail() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail(null);
        request.setPassword(USER_PASSWORD);
        request.setRole(UserRole.ROLE_USER.toString());
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @WithAnonymousUser
    void signupUserInvalidBlankEmail() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("");
        request.setPassword(USER_PASSWORD);
        request.setRole(UserRole.ROLE_USER.toString());
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @WithAnonymousUser
    void signupUserInvalidNullPassword() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail(USER_1_MAIL);
        request.setRole(UserRole.ROLE_USER.toString());
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @WithAnonymousUser
    void signupUserInvalidBlankPassword() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail(USER_1_MAIL);
        request.setPassword("");
        request.setRole(UserRole.ROLE_USER.toString());
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    @WithAnonymousUser
    void authenticateUser() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail(USER_1_MAIL);
        request.setPassword(USER_PASSWORD);
        request.setRole(UserRole.ROLE_USER.toString());
        JWTToken token = new JWTToken(request.getEmail(), VALID_TOKEN);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(null);
        when(userService.login(any(LoginRequest.class))).thenReturn(token);
        perform(post(REST_URL + "login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.access_token").value(VALID_TOKEN));
    }

    @Test
    @WithAnonymousUser
    void getUnAuth() throws Exception {
        perform(get(REST_URL + "me"))
                .andExpect(status().isForbidden());
    }

}
