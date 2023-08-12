package com.garage.garage.service;

import com.garage.garage.model.SignUpRequest;
import com.garage.garage.model.SigninRequest;
import com.garage.garage.model.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
