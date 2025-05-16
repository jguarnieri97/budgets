package ar.edu.unlam.tpi.budgets.service;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetCreationRequestDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetRequestListResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetRequestResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetResponseDto;

import java.util.List;

public interface BudgetService {

    BudgetCreationResponseDto create(BudgetCreationRequestDto request);
    List<BudgetRequestResponseDto> getBudgetsByApplicantId(Long aplicantId);
    BudgetResponseDto getBudgetDetailById(String budgetId);

}
