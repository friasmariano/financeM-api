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
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
    public ResponseEntity<ApiDefaultResponse<BudgetResponse>> create(@Valid @RequestBody BudgetRequest request, @AuthenticationPrincipal Jwt jwt) {
        User user = userService.getAuthenticatedUser(jwt);
        BudgetCategory category = categoryService.getById(request.getCategoryId())
                .orElseThrow(() -> new BudgetCategoryNotFoundException("Category not found"));

        Budget createdB = new Budget();
        createdB.setCategory(category);
        createdB.setLimitAmount(request.getLimitAmount());
        createdB.setUser(user);

        Budget saved = service.create(createdB);

        BudgetResponse response = new BudgetResponse(saved);

        ApiDefaultResponse<BudgetResponse> apiDefaultResponse = new ApiDefaultResponse<>(true, response, "Budget created successfully");

        return ResponseEntity.ok(apiDefaultResponse);
    }
}
