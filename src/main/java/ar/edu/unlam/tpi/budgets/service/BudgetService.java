package ar.edu.unlam.tpi.budgets.service;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetUpdateDataRequestDto;
import ar.edu.unlam.tpi.budgets.dto.request.BudgetFinalizeRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDetailDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetSupplierResponseDto;

import java.util.List;

public interface BudgetService {

    BudgetCreationResponseDto create(BudgetCreationRequestDto request);
    List<BudgetResponseDto> getBudgetsByApplicantId(Long applicantId);
    List<BudgetSupplierResponseDto> getBudgetsBySupplierId(Long supplierId);
    BudgetResponseDetailDto getBudgetDetailById(String budgetId);
    void update(String id, Long providerId,  BudgetUpdateDataRequestDto request);
    void finalizeBudgetRequest(String budgetId, BudgetFinalizeRequestDto request);
    void finalizeRequestOnly(String id);
}
