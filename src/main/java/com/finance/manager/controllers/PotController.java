package com.finance.manager.controllers;

import com.finance.manager.exceptions.AccessDeniedPotOperationException;
import com.finance.manager.exceptions.PotNotFoundException;
import com.finance.manager.models.Budget;
import com.finance.manager.models.Pot;
import com.finance.manager.models.User;
import com.finance.manager.models.requests.PotRequest;
import com.finance.manager.models.responses.ApiDefaultResponse;
import com.finance.manager.models.responses.PotResponse;
import com.finance.manager.repositories.BudgetRepository;
import com.finance.manager.services.PotService;
import com.finance.manager.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pots")
@Tag(name = "Pots", description = "Handles pot creation, retrieval, updating and deletion")
public class PotController {

    private final PotService potService;
    private final UserService userService;
    private final BudgetRepository budgetRepository;

    public PotController(PotService potService, UserService userService, BudgetRepository budgetRepository) {
        this.potService = potService;
        this.userService = userService;
        this.budgetRepository = budgetRepository;
    }

    @PostMapping
    @Operation(summary = "Create a new Pot")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Pot created successfully") })
    public ResponseEntity<ApiDefaultResponse<PotResponse>> create(@Valid @RequestBody PotRequest request, @AuthenticationPrincipal Jwt jwt) {
        User user = userService.getAuthenticatedUser(jwt);

        Pot pot = new Pot();
        pot.setName(request.getName());
        pot.setGoalAmount(request.getGoalAmount());
        pot.setCurrentAmount(request.getCurrentAmount());
        pot.setUser(user);

        if (request.getBudgetId() != null) {
            Budget budget = budgetRepository.findById(request.getBudgetId())
                    .orElseThrow(() -> new RuntimeException("Budget not found"));
            pot.setBudget(budget);
        } else {
            pot.setBudget(null);
        }

        Pot createdPot = potService.create(pot);

        PotResponse response = new PotResponse(
                createdPot.getId(),
                createdPot.getName(),
                createdPot.getGoalAmount(),
                createdPot.getCurrentAmount(),
                createdPot.getUser().getId(),
                createdPot.getBudget() != null ? createdPot.getBudget().getId() : null
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiDefaultResponse.success(response, "Pot created successfully!"));
    }

    @GetMapping
    @Operation(summary = "Get all pots for the authenticated user")
    public ApiDefaultResponse<List<PotResponse>> getAllByUser(@AuthenticationPrincipal Jwt jwt) {
        User user = userService.getAuthenticatedUser(jwt);
        List<Pot> pots = potService.getByUser(user);

        List<PotResponse> potResponses = pots.stream()
                .map(pot -> new PotResponse(
                        pot.getId(),
                        pot.getName(),
                        pot.getGoalAmount(),
                        pot.getCurrentAmount(),
                        pot.getUser().getId(),
                        pot.getBudget() != null ? pot.getBudget().getId() : null
                ))
                .toList();

        return ApiDefaultResponse.success(potResponses, "Pots retrieved successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific pot by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pot retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User is not allowed to access this pot"),
            @ApiResponse(responseCode = "404", description = "Pot not found")
    })
    public ApiDefaultResponse<Pot> getById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        User user = userService.getAuthenticatedUser(jwt);
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
            @ApiResponse(responseCode = "404", description = "Pot not found or user is not allowed to update this pot")
    })
    public ResponseEntity<ApiDefaultResponse<PotResponse>> update(@PathVariable Long id, @Valid @RequestBody PotRequest potUpdate, @AuthenticationPrincipal Jwt jwt) {
        User user = userService.getAuthenticatedUser(jwt);
        Pot existingPot = potService.getById(id)
                .orElseThrow(() -> new PotNotFoundException("Pot not found"));

        if (!existingPot.getUser().getId().equals(user.getId())) {
              throw new AccessDeniedPotOperationException("You are not allowed to update this pot.");
        }

        existingPot.setUser(user);
        existingPot.setName(potUpdate.getName());
        existingPot.setGoalAmount(potUpdate.getGoalAmount());
        existingPot.setCurrentAmount(potUpdate.getCurrentAmount());

        if (potUpdate.getBudgetId() != null) {
            Budget budget = budgetRepository.findById(potUpdate.getBudgetId())
                    .orElseThrow(() -> new RuntimeException("Budget not found"));
            existingPot.setBudget(budget);
        } else {
            existingPot.setBudget(null);
        }

        Pot updatedPot = potService.update(id, existingPot);

        PotResponse response = new PotResponse(
                updatedPot.getId(),
                updatedPot.getName(),
                updatedPot.getGoalAmount(),
                updatedPot.getCurrentAmount(),
                updatedPot.getUser().getId(),
                updatedPot.getBudget() != null ? updatedPot.getBudget().getId() : null
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiDefaultResponse.success(response, "Pot updated successfully!"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a pot by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pot deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pot not found or user is not allowed to delete this pot")
    })
    public ResponseEntity<ApiDefaultResponse<PotResponse>> delete(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        User user = userService.getAuthenticatedUser(jwt);

        Pot pot = potService.findByIdAndUser(id, user);
        potService.delete(id, user);

        PotResponse response = new PotResponse(
                pot.getId(),
                pot.getName(),
                pot.getGoalAmount(),
                pot.getCurrentAmount(),
                pot.getUser().getId(),
                pot.getBudget() != null ? pot.getBudget().getId() : null
        );

        return ResponseEntity.ok(ApiDefaultResponse.success(response, "Pot deleted successfully!"));
    }
}
