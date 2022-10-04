package com.truestore.backend.user;

import com.truestore.backend.security.JWTToken;
import com.truestore.backend.security.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public JWTToken login(LoginRequest loginRequest) {
        User user = getByEmail(loginRequest.getEmail());
        String token = jwtUtil.generateToken(user.getId());
        return new JWTToken(user.getId(), token);
    }

    public JWTToken signup(LoginRequest loginRequest) {
        User user = new User(loginRequest.getEmail(), passwordEncoder.encode(loginRequest.getPassword()), loginRequest.getRole());
        User registratedUser = userRepository.addUser(user).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.CONFLICT, "Unable to add user")
        );
        String token = jwtUtil.generateToken(registratedUser.getId());
        return new JWTToken(registratedUser.getId(), token);
    }

    public User getUserInfo(String userId) {
        return userRepository.getUser(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }

    public User getByEmail(String email) {
        return userRepository.getUserByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }
}
