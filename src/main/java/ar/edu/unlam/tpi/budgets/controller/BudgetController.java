package ar.edu.unlam.tpi.budgets.controller;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequest;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponse;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetRequestListResponse;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.budgets.dto.response.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("budgets/v1/budget")
@Validated
public interface BudgetController {

    @GetMapping("/user/{applicantId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get applicant's budget requests")
    GenericResponse<BudgetRequestListResponse> getBudgetsByApplicantId(
            @PathVariable("applicantId") @NotNull Long applicantId);

    @GetMapping("/{budgetId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get budget detail by id")
    GenericResponse<BudgetResponse> getBudgetDetailById(
            @PathVariable("budgetId") @NotNull String budgetId);

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create budget request")
    GenericResponse<BudgetCreationResponse> createBudget(
            @Valid @RequestBody BudgetCreationRequest request);

}
