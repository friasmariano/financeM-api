
package com.finance.manager.controllers;

import com.finance.manager.models.responses.TokenResponse;
import com.finance.manager.services.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    public final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/authenticate")
    public TokenResponse token(Authentication authentication, HttpServletResponse response) {
        LOG.debug("Token request for user: {}", authentication.getName());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token granted: {}", token);

        // Set up the HTTP-only cookie
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);

        String cookieHeader = String.format(
                "jwt=%s; HttpOnly; Secure; Path=/; Max-Age=3600; SameSite=Strict",
                token
        );
        response.addHeader("Set-Cookie", cookieHeader);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(token);
        tokenResponse.setExpiresIn(3600);
        tokenResponse.setUsername(authentication.getName());

        return tokenResponse;
    }
}
