
package com.finance.manager.controllers;

import com.finance.manager.models.requests.LoginRequest;
import com.finance.manager.models.responses.TokenResponse;
import com.finance.manager.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    public final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final HttpServletResponse response;

    public AuthController(TokenService tokenService,
                          HttpServletResponse response,
                          AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.response = response;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate user and return JWT token")
    @ApiResponse(responseCode = "200", description = "Authentication successful")
    public TokenResponse token(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        LOG.debug("Token request for user: {}", loginRequest.getUsername());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token granted: {}", token);

        // Set up the HTTP-only cookie
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Only for development; set to true in production
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        cookie.setAttribute("SameSite", "Lax");
        response.addCookie(cookie);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(token);
        tokenResponse.setExpiresIn(3600);
        tokenResponse.setUsername(authentication.getName());

        return tokenResponse;
    }

    @PostMapping("/logout")
    @Operation(summary = "Log out user by clearing JWT cookie")
    @ApiResponse(responseCode = "204", description = "Logout successful")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {
        Cookie cookie = new Cookie("jwt", "");

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "Lax");

        response.addCookie(cookie);
    }

}
