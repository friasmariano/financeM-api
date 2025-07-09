package com.finance.manager.controllers;

import com.finance.manager.exceptions.AccessDeniedPotOperationException;
import com.finance.manager.exceptions.PotNotFoundException;
import com.finance.manager.exceptions.UserNotFoundException;
import com.finance.manager.models.Pot;
import com.finance.manager.models.User;
import com.finance.manager.models.requests.CreatePotRequest;
import com.finance.manager.models.responses.ApiDefaultResponse;
import com.finance.manager.repositories.UserRepository;
import com.finance.manager.services.PotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pots")
public class PotController {

    private final PotService potService;
    private final UserRepository userRepository;

    public PotController(PotService potService, UserRepository userRepository) {
        this.potService = potService;
        this.userRepository = userRepository;
    }

    private User getAuthenticatedUser(Jwt jwt) {
        String subject = jwt.getSubject();
        return userRepository.findByUsername(subject)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @PostMapping
    @Operation(summary = "Create a new Pot")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pot created successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated")
    })
    public ApiDefaultResponse<Pot> create(@Valid @RequestBody CreatePotRequest request, @AuthenticationPrincipal Jwt jwt) {
        System.out.println("JWT sub: " + jwt.getSubject());
        User user = getAuthenticatedUser(jwt);
        //
        Pot pot = new Pot();
        pot.setName(request.getName());
        pot.setGoalAmount(request.getGoalAmount());
        pot.setCurrentAmount(request.getCurrentAmount());
        pot.setUser(user);

        Pot createdPot = potService.create(pot);
        return ApiDefaultResponse.success(createdPot, "Pot created successfully!");
    }

    @GetMapping
    @Operation(summary = "Get all pots for the authenticated user")
    public ApiDefaultResponse<List<Pot>> getAllByUser(@AuthenticationPrincipal Jwt jwt) {
        User user = getAuthenticatedUser(jwt);
        List<Pot> pots = potService.getByUser(user);

        return ApiDefaultResponse.success(pots, "Pots retrieved successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific pot by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pot retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User is not allowed to access this pot"),
            @ApiResponse(responseCode = "404", description = "Pot not found")
    })
    public ApiDefaultResponse<Pot> getById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        User user = getAuthenticatedUser(jwt);
        Pot pot = potService.getById(id)
                .orElseThrow(() -> new PotNotFoundException("Pot not found"));

        if (!pot.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedPotOperationException("You cannot access this pot");
        }

        return ApiDefaultResponse.success(pot, "Pot retrieved successfully");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing pot")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pot updated successfully"),
            @ApiResponse(responseCode = "404", description = "User is not allowed to update this pot"),
            @ApiResponse(responseCode = "404", description = "Pot not found")
    })
    public ApiDefaultResponse<Pot> update(@PathVariable Long id, @Valid @RequestBody Pot potUpdate, @AuthenticationPrincipal Jwt jwt) {
        User user = getAuthenticatedUser(jwt);
        Pot existingPot = potService.getById(id)
                .orElseThrow(() -> new PotNotFoundException("Pot not found"));

        if (!existingPot.getUser().getId().equals(user.getId())) {
              throw new AccessDeniedPotOperationException("You are not allowed to update this pot.");
        }

        potUpdate.setUser(user);
        Pot updatedPot = potService.update(id, potUpdate);
        return ApiDefaultResponse.success(updatedPot, "Pot updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a pot by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pot deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User is not allowed to delete this pot"),
            @ApiResponse(responseCode = "404", description = "Pot not found")
    })
    public ApiDefaultResponse<String> delete(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        User user = getAuthenticatedUser(jwt);
        potService.delete(id, user);

        return ApiDefaultResponse.success(null, "Pot deleted successfully");
    }
}
