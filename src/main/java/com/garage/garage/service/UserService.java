package com.garage.garage.service;

import com.garage.garage.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();

    UserDetails loadUserByJwtToken(String jwtToken);

    User loadUserById(Integer id);


}
