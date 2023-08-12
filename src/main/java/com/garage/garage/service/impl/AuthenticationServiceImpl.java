package com.garage.garage.service.impl;

import com.garage.garage.model.*;
import com.garage.garage.repository.UserRepository;
import com.garage.garage.service.AuthenticationService;
import com.garage.garage.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.garage.garage.model.User;

// Mostly from https://medium.com/@truongbui95/jwt-authentication-and-authorization-with-spring-boot-3-and-spring-security-6-2f90f9337421
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {

        var user = userRepository.findByEmail(request.getEmail())
                .orElse(new User()); // Create an empty Users object if email is not found

        if (user.getId() == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // Redirection not working yet, sends user to Home
            return new JwtAuthenticationResponse("redirect:/auth/invalid");
        }

        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
