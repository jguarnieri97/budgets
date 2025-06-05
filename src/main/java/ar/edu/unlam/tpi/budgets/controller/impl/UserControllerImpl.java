package ar.edu.unlam.tpi.budgets.controller.impl;

import ar.edu.unlam.tpi.budgets.controller.UserController;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetRequestResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.BudgetSupplierResponseDto;
import ar.edu.unlam.tpi.budgets.dto.response.GenericResponse;
import ar.edu.unlam.tpi.budgets.service.BudgetService;
import ar.edu.unlam.tpi.budgets.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final BudgetService budgetService;

    @Override
    public GenericResponse<List<BudgetRequestResponseDto>> getBudgetsByApplicantId(Long applicantId) {
        List<BudgetRequestResponseDto> budgetList = budgetService.getBudgetsByApplicantId(applicantId);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                budgetList);    
    }

    @Override
    public GenericResponse<List<BudgetSupplierResponseDto>> getBudgetsBySupplierId(Long supplierId) {
        List<BudgetSupplierResponseDto> budgetList = budgetService.getBudgetsBySupplierId(supplierId);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                budgetList);
    }
}
