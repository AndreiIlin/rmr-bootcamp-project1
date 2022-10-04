package com.truestore.backend.user;

import com.truestore.backend.AbstractControllerTest;
import com.truestore.backend.security.JWTToken;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithAnonymousUser;

import static com.truestore.backend.user.UserTestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractControllerTest {
    private static final String REST_URL = UserController.REST_URL + '/';
    @MockBean
    private UserService userService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @Test
    @WithAnonymousUser
    void signupUser() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail(USER_MAIL);
        request.setPassword(USER_PASSWORD);
        request.setRole(UserRole.ROLE_USER.toString());
        JWTToken token = new JWTToken(request.getEmail(), VALID_TOKEN);
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
    void signupUserInvalid() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail(null);
        request.setPassword(null);
        JWTToken token = new JWTToken(request.getEmail(), VALID_TOKEN);
        when(userService.signup(any(LoginRequest.class))).thenReturn(token);
        perform(post(REST_URL + "signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithAnonymousUser
    void signupUserInvalidBlankEmail() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("");
        request.setPassword("pass");
        JWTToken token = new JWTToken(request.getEmail(), VALID_TOKEN);
        when(userService.signup(any(LoginRequest.class))).thenReturn(token);
        perform(post(REST_URL + "signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithAnonymousUser
    void authenticateUser() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail(USER_MAIL);
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
