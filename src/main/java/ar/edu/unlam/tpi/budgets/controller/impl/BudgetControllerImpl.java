package ar.edu.unlam.tpi.budgets.controller.impl;

import ar.edu.unlam.tpi.budgets.controller.BudgetController;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequest;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponse;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetRequestListResponse;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.budgets.dto.response.GenericResponse;
import ar.edu.unlam.tpi.budgets.service.BudgetService;
import ar.edu.unlam.tpi.budgets.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BudgetControllerImpl implements BudgetController {

    private final BudgetService budgetService;

    @Override
    public GenericResponse<BudgetRequestListResponse> getBudgetsByApplicantId(Long applicantId) {
        BudgetRequestListResponse budgetList = budgetService.getBudgetsByApplicantId(applicantId);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                budgetList);
    }

    @Override
    public GenericResponse<BudgetResponse> getBudgetDetailById(String budgetId) {
        BudgetResponse budget = budgetService.getBudgetDetailById(budgetId);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                budget);
    }

    @Override
    public GenericResponse<BudgetCreationResponse> createBudget(BudgetCreationRequest request) {
        BudgetCreationResponse budget = budgetService.create(request);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                budget);
    }

}
