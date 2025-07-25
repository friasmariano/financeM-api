package com.finance.manager.controllers;

import com.finance.manager.exceptions.BudgetCategoryNotFoundException;
import com.finance.manager.models.Budget;
import com.finance.manager.models.BudgetCategory;
import com.finance.manager.models.User;
import com.finance.manager.models.requests.BudgetRequest;
import com.finance.manager.models.responses.ApiDefaultResponse;
import com.finance.manager.models.responses.BudgetResponse;
import com.finance.manager.services.BudgetCategoryService;
import com.finance.manager.services.BudgetService;
import com.finance.manager.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@Tag(name = "Budgets", description = "Handles budget creation, retrieval, updating and deletion")
public class BudgetController {

    private final BudgetService service;
    private final BudgetCategoryService categoryService;
    private final UserService userService;

    public BudgetController(BudgetService service, BudgetCategoryService categoryService, UserService userService) {
        this.service = service;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new Budget")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Budget created successfully") })
    public ResponseEntity<ApiDefaultResponse<BudgetResponse>> create(@Valid @RequestBody BudgetRequest request, @AuthenticationPrincipal Jwt jwt) {
        User user = userService.getAuthenticatedUser(jwt);
        BudgetCategory category = categoryService.getById(request.getCategoryId())
                .orElseThrow(() -> new BudgetCategoryNotFoundException("Category not found"));

        Budget created = new Budget();
        created.setCategory(category);
        created.setLimitAmount(request.getLimitAmount());
        created.setUser(user);

        Budget saved = service.create(created);

        return ResponseEntity.ok(new ApiDefaultResponse<> (true, new BudgetResponse(saved), "Budget created successfully!"));
    }

    @GetMapping
    @Operation(summary = "Get all budgets for the current user")
    public ResponseEntity<ApiDefaultResponse<List<BudgetResponse>>> getAll(@AuthenticationPrincipal Jwt jwt) {
        User user = userService.getAuthenticatedUser(jwt);

        List<BudgetResponse> response = service.getByUser(user)
                .stream()
                .map(BudgetResponse::new)
                .toList();

        return ResponseEntity.ok(new ApiDefaultResponse<>(true, response, "Budgets retrieved successfully!"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get budget by ID")
    public ResponseEntity<ApiDefaultResponse<BudgetResponse>> getById(@PathVariable Long id) {
        Budget budget = service.getById(id);

        return ResponseEntity.ok(new ApiDefaultResponse<>(true, new BudgetResponse(budget), "Budget found."));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update the specified budget")
    public ResponseEntity<ApiDefaultResponse<BudgetResponse>> update(@PathVariable Long id,
                                                                     @Valid @RequestBody BudgetRequest request,
                                                                     @AuthenticationPrincipal Jwt jwt) {
        User user = userService.getAuthenticatedUser(jwt);
        BudgetCategory category = categoryService.getById(request.getCategoryId())
                .orElseThrow(() -> new BudgetCategoryNotFoundException("Category not found."));

        Budget updated = new Budget();
        updated.setId(id);
        updated.setCategory(category);
        updated.setLimitAmount(request.getLimitAmount());
        updated.setUser(user);

        Budget saved = service.update(id, updated);

        return ResponseEntity.ok(new ApiDefaultResponse<>(true, new BudgetResponse(saved), "Budget updated successfully!"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete the specified budget")
    public ResponseEntity<ApiDefaultResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.ok(new ApiDefaultResponse<>(true, null, "Budget deleted successfully!"));
    }
}
