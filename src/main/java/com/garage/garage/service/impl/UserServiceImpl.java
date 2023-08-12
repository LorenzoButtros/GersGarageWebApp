package com.garage.garage.service.impl;

import com.garage.garage.model.User;
import com.garage.garage.repository.UserRepository;
import com.garage.garage.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// Mostly from https://medium.com/@truongbui95/jwt-authentication-and-authorization-with-spring-boot-3-and-spring-security-6-2f90f9337421
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtServiceImpl jwtService;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public UserDetails loadUserByJwtToken(String jwtToken) {
        String userEmail = jwtService.extractUserName(jwtToken);
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Integer getUserIdFromJwtToken(HttpServletRequest request) {
        String jwtToken = jwtService.getJwtTokenFromCookie(request);
        if (jwtToken != null) {
            String userEmail = jwtService.extractUsernameFromCookie(request);
            if (userEmail != null) {
                Optional<User> userOptional = userRepository.findByEmail(userEmail);
                return userOptional.map(User::getId).orElse(null);
            }
        }
        return null;
    }

    @Override
    public User loadUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Map<Integer, String> getUserNamesMap() {

        List<User> users = userRepository.findAll();
        Map<Integer, String> userNamesMap = new HashMap<>();
        for (User user : users) {
            userNamesMap.put(user.getId(), user.getFirstName() + " " + user.getLastName());
        }
        return userNamesMap;
    }
}

