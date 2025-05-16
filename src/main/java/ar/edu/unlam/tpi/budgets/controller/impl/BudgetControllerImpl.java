package ar.edu.unlam.tpi.budgets.controller.impl;

import ar.edu.unlam.tpi.budgets.controller.BudgetController;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.*;
import ar.edu.unlam.tpi.budgets.service.BudgetService;
import ar.edu.unlam.tpi.budgets.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
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
    public GenericResponse<BudgetCreationResponseDto> createBudget(BudgetCreationRequestDto request) {
        BudgetCreationResponseDto budget = budgetService.create(request);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                budget);
    }

}
