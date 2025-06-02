package ar.edu.unlam.tpi.budgets.utils;

import org.springframework.stereotype.Component;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetUpdateRequestDto;
import ar.edu.unlam.tpi.budgets.model.Budget;
import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;
import ar.edu.unlam.tpi.budgets.model.enums.BudgetState;



@Component
public class BudgetValidator {


    // validar que el estado nuevo no sea el mismo que el estado actual
    // si es FINALIZED, validar que el supplierHired no sea null
    // si es FINALIZED, setear el estado de los presupuestos a ACCEPTED si el supplierId coincide con el supplierHired
    public void validateAndApplyStateTransition(BudgetRequestEntity budgetRequest, BudgetUpdateRequestDto request) {
        BudgetState newState = request.getState();

        if (newState == budgetRequest.getState()) {
            throw new IllegalArgumentException("El presupuesto ya se encuentra en ese estado.");
        }

        budgetRequest.setState(newState);

        if (newState == BudgetState.FINALIZED) {
            Long supplierId = request.getSupplierHired();
            for (Budget budget : budgetRequest.getBudgets()) {
                boolean isHired = budget.getSupplierId().equals(supplierId);
                budget.setHired(isHired);
                if (isHired) {
                    budget.setState(BudgetState.ACCEPTED);
                }
            }
        }
    }
}
