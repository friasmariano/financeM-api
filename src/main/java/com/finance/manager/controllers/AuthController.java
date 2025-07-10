
package com.finance.manager.controllers;

import com.finance.manager.exceptions.InvalidCredentialsException;
import com.finance.manager.exceptions.TooManyAttemptsException;
import com.finance.manager.models.Person;
import com.finance.manager.models.User;
import com.finance.manager.models.requests.AuthRequest;
import com.finance.manager.models.responses.ApiDefaultResponse;
import com.finance.manager.models.responses.LogoutResponse;
import com.finance.manager.models.responses.UserResponse;
import com.finance.manager.security.RateLimiterService;
import com.finance.manager.services.PersonService;
import com.finance.manager.services.TokenService;
import com.finance.manager.services.UserService;
import io.github.bucket4j.Bucket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    public final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PersonService personService;
    private final UserService userService;
    private final RateLimiterService rateLimiterService;

    public AuthController(TokenService tokenService, HttpServletResponse response,
                          AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                          PersonService personService, UserService userService, RateLimiterService rateLimiterService) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.personService = personService;
        this.userService = userService;
        this.rateLimiterService = rateLimiterService;
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate user and return user data")
    @ApiResponse(responseCode = "200", description = "Authentication successful")
    public ResponseEntity<ApiDefaultResponse<UserResponse>>
                           validateSession(@RequestBody AuthRequest authRequest,
                                           HttpServletResponse response) {

        Bucket bucket = rateLimiterService.resolveBucket(authRequest.getEmail());

        if (!bucket.tryConsume(1)) {
            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(ApiDefaultResponse.error("Too many attempts. Please try again later."));
        }

        Optional<Person> personOpt = personService.findByEmail(authRequest.getEmail());
        if (personOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiDefaultResponse.error("Invalid email or password."));
        }
        Person person = personOpt.get();

        Optional<User> userOpt = userService.findByPerson(person);
        if (userOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiDefaultResponse.error("Invalid email or password."));
        }
        User user = userOpt.get();


        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), authRequest.getPassword())
            );

        } catch (AuthenticationException ex) {
            LOG.warn("Authentication failed for user: {}", authRequest.getEmail());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiDefaultResponse.error("Invalid email or password."));
        }

        LOG.debug("Token request for user: {}", user.getUsername());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token successfully generated for user '{}'", user.getUsername());

        // Set up the HTTP-only cookie
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Only for development; set to true in production
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        cookie.setAttribute("SameSite", "Lax");
        response.addCookie(cookie);

        UserResponse userResponse = UserResponse.fromEntity(user);
        return ResponseEntity.ok(ApiDefaultResponse.success(userResponse, "Authentication successful!"));
    }

    @PostMapping("/logout")
    @Operation(summary = "Log out user by clearing JWT cookie")
    @ApiResponse(responseCode = "200", description = "Logout successful")
    public ResponseEntity<ApiDefaultResponse<LogoutResponse>> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "Lax");

        response.addCookie(cookie);

        LogoutResponse logoutData = new LogoutResponse(Instant.now());
        return ResponseEntity.ok(ApiDefaultResponse.success(logoutData, "User logged out successfully!"));
    }

}
