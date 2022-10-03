package com.truestore.backend.user;

import com.truestore.backend.security.JWTToken;
import com.truestore.backend.security.JWTUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.truestore.backend.user.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JWTUtil jwtUtil;
    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void signupUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(USER_MAIL);
        loginRequest.setPassword(USER_PASSWORD);
        User user = new User(loginRequest.getEmail(), loginRequest.getPassword());
        when(jwtUtil.generateToken(Mockito.anyString())).thenReturn(VALID_TOKEN);
        when(userRepository.addUser(any(User.class))).thenReturn(Optional.of(user));
        JWTToken result = userService.signup(loginRequest);
        assertEquals(USER_MAIL, result.getUserEmail());
        assertEquals(VALID_TOKEN, result.getAccessToken());
    }

    @Test
    void loginUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(USER_MAIL);
        loginRequest.setPassword(USER_PASSWORD);
        when(jwtUtil.generateToken(Mockito.anyString())).thenReturn(VALID_TOKEN);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(null);
        JWTToken result = userService.login(loginRequest);
        assertEquals(USER_MAIL, result.getUserEmail());
        assertEquals(VALID_TOKEN, result.getAccessToken());
    }
}
