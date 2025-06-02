package ar.edu.unlam.tpi.budgets.controller.impl;

import ar.edu.unlam.tpi.budgets.controller.BudgetController;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetUpdateRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.*;
import ar.edu.unlam.tpi.budgets.service.BudgetService;
import ar.edu.unlam.tpi.budgets.utils.Constants;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
public class BudgetControllerImpl implements BudgetController {

    private final BudgetService budgetService;

    @Override
    public GenericResponse<BudgetResponseDto> getBudgetDetailById(String budgetId) {
        BudgetResponseDto budget = budgetService.getBudgetDetailById(budgetId);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                budget);
    }

    @Override
    public GenericResponse<BudgetCreationResponseDto> createBudget(
            @Valid @RequestBody BudgetCreationRequestDto request) {
        BudgetCreationResponseDto budget = budgetService.create(request);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                budget);
    }

    @Override
    public GenericResponse<Void> updateBudget(String budgetId, BudgetUpdateRequestDto request) {
        budgetService.update(budgetId, request);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.UPDATED_MESSAGE,
                null);
    }

}