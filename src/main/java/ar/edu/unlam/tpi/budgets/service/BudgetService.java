package ar.edu.unlam.tpi.budgets.service;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequest;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponse;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetRequestListResponse;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponse;

public interface BudgetService {

    BudgetCreationResponse create(BudgetCreationRequest request);
    BudgetRequestListResponse getBudgetsByApplicantId(Long aplicantId);
    BudgetResponse getBudgetDetailById(String budgetId);

}
