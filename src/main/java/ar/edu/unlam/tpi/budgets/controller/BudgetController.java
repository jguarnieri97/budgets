package ar.edu.unlam.tpi.budgets.controller;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetUpdateDataRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetFinalizeRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequestMapping("budgets/v1/budget")
@Validated
public interface BudgetController {


    @GetMapping("/{budgetId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get budget detail by id")
    GenericResponse<BudgetResponseDto> getBudgetDetailById(
            @PathVariable("budgetId") @NotNull String budgetId);

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create budget request")
    GenericResponse<BudgetCreationResponseDto> createBudget(
            @Valid @RequestBody BudgetCreationRequestDto request);

    @PutMapping("/{id}/user/{providerId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update budget request")
    GenericResponse<Void> updateBudget(@PathVariable String id, @PathVariable Long providerId,
            @Valid @RequestBody BudgetUpdateDataRequestDto request);

    @PutMapping("/{budgetId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Finalize budget request")
    GenericResponse<Void> finalizeBudgetRequest(@PathVariable String budgetId,
            @Valid @RequestBody BudgetFinalizeRequestDto request);

}
