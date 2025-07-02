
package com.finance.manager.controllers;

import com.finance.manager.models.Person;
import com.finance.manager.models.User;
import com.finance.manager.models.requests.AuthRequest;
import com.finance.manager.models.requests.LoginRequest;
import com.finance.manager.models.responses.ApiDefaultResponse;
import com.finance.manager.models.responses.TokenResponse;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    public final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletResponse response;
    private final PersonService personService;
    private final UserService userService;
    private final RateLimiterService rateLimiterService;

    public AuthController(TokenService tokenService,
                          HttpServletResponse response,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder,
                          PersonService personService,
                          UserService userService,
                          RateLimiterService rateLimiterService) {
        this.tokenService = tokenService;
        this.response = response;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.personService = personService;
        this.userService = userService;
        this.rateLimiterService = rateLimiterService;
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

    @PostMapping("/validateSession")
    @Operation(summary = "Authenticate user and return user data")
    @ApiResponse(responseCode = "200", description = "Authentication successful")
    public ResponseEntity<ApiDefaultResponse<UserResponse>> validateSession(@RequestBody AuthRequest authRequest) {

//        Bucket bucket = rateLimiterService.resolveBucket(authRequest.getEmail());
//
//        if (!bucket.tryConsume(1)) {
//            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
//                    .body(ApiDefaultResponse.error("Too many attempts. Please try again later"));
//        }

        Optional<Person> personOpt = personService.findByEmail(authRequest.getEmail());

        if (personOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiDefaultResponse.error("Invalid email or password."));
        }

        Person person = personOpt.get();
        Optional<User> userOpt = userService.findByPerson(person);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiDefaultResponse.error("Invalid email or password."));
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiDefaultResponse.error("Invalid email or password"));
        }

        UserResponse userResponse = UserResponse.fromEntity(user);

        return ResponseEntity.ok(ApiDefaultResponse.success(userResponse, "Authentication successful!"));
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
