package com.garage.garage.controller;

import com.garage.garage.model.SignUpRequest;
import com.garage.garage.model.SigninRequest;
import com.garage.garage.model.JwtAuthenticationResponse;
import com.garage.garage.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new SignUpRequest());
        return "signup";
    }

    @PostMapping("/signup")

    public RedirectView signup(@ModelAttribute("user") SignUpRequest request,
                               HttpServletResponse response) {
        JwtAuthenticationResponse authenticationResponse = authenticationService.signup(request);

        // Set the token as a cookie in the response
        Cookie cookie = new Cookie("jwtToken", authenticationResponse.getToken());
        cookie.setMaxAge(24 * 60 * 60); // Set the cookie expiration time (e.g., 24 hours)
        cookie.setPath("/"); // Set the cookie path to the root context
        response.addCookie(cookie);

        return new RedirectView("/auth/done");

    }

    @GetMapping("/done")
    public String showSignupDone(Model model) {
        return "signup-done";
    }

    @GetMapping("/signin")
    public String showSigninForm(Model model) {

        model.addAttribute("user", new SigninRequest());
        return "signin";
    }

    @PostMapping("/signin")
    public RedirectView signin(@ModelAttribute("user") SigninRequest request,
                               HttpServletResponse response) {

        JwtAuthenticationResponse authenticationResponse = authenticationService.signin(request);

        // Set the token as a cookie in the response
        Cookie cookie = new Cookie("jwtToken", authenticationResponse.getToken());
        cookie.setMaxAge(24 * 60 * 60); // Set the cookie expiration time (24 hours)
        cookie.setPath("/"); // Set the cookie path to the root context
        response.addCookie(cookie);

        // Redirect to the home page after successful authentication
        return new RedirectView("/");
    }

    @GetMapping("/signout")
    public RedirectView signout(HttpServletRequest request, HttpServletResponse response) {

        // Clear the "jwtToken" cookie by setting its value to an empty string
        Cookie cookie = new Cookie("jwtToken", "");
        cookie.setMaxAge(0); // Set the cookie's expiration time to 0 to immediately expire it
        cookie.setPath("/"); // Set the cookie path to the root context to ensure it is removed from the entire application
        response.addCookie(cookie);

        // Redirect to the home page after logout
        return new RedirectView("/");
    }

    @GetMapping("/invalid")
    public String showinvalid() {
        return "signin-invalid";
    }

}

