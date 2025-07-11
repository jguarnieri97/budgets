package ar.edu.unlam.tpi.budgets.controller;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetUpdateDataRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetFinalizeRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("budgets/v1/budget")
@Validated
public interface BudgetController {

        @GetMapping("/{budgetId}")
        @ResponseStatus(HttpStatus.OK)
        @Operation(summary = "Get budget detail by id")
        GenericResponse<BudgetResponseDetailDto> getBudgetDetailById(
                        @PathVariable("budgetId") @NotNull Long budgetId);

        @PostMapping
        @ResponseStatus(HttpStatus.OK)
        @Operation(summary = "Create budget request")
        GenericResponse<BudgetCreationResponseDto> createBudget(
                        @Valid @RequestBody BudgetCreationRequestDto request);

        @PutMapping("/{id}/user/{providerId}")
        @ResponseStatus(HttpStatus.OK)
        @Operation(summary = "Update budget request")
        GenericResponse<Void> updateBudget(@PathVariable Long id, @PathVariable Long providerId,
                        @Valid @RequestBody BudgetUpdateDataRequestDto request);

        @PutMapping("/{budgetId}")
        @ResponseStatus(HttpStatus.OK)
        @Operation(summary = "Finalize budget request")
        GenericResponse<Void> finalizeBudgetRequest(@PathVariable Long budgetId,
                        @Valid @RequestBody BudgetFinalizeRequestDto request);

        @PutMapping("/{id}/finalize-request")
        @ResponseStatus(HttpStatus.OK)
        @Operation(summary = "Finalize budget request")
        GenericResponse<Void> finalizeRequestOnly(@PathVariable Long id);

        @PutMapping("/{id}/user/{providerId}/reject")
        @ResponseStatus(HttpStatus.OK)
        @Operation(summary = "Reject budget request")
        GenericResponse<Void> rejectBudgetRequest(@PathVariable("id") Long id,
                                                  @PathVariable("providerId") Long providerId);

}
