package ar.edu.unlam.tpi.budgets.utils;

import org.springframework.stereotype.Component;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetFinalizeRequestDto;
import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;
import ar.edu.unlam.tpi.budgets.model.BudgetState;
import ar.edu.unlam.tpi.budgets.exceptions.NotFoundException;



@Component
public class BudgetValidator {

    public void validateSupplierHired(BudgetRequestEntity budgetRequest, BudgetFinalizeRequestDto request) {
        Long supplierHired = request.getSupplierHired();
        if (supplierHired == null) {
            throw new IllegalArgumentException("El proveedor contratado no puede ser null");
        }

        //verificar que el proveedor contratado sea un proveedor valido de la lista de proveedoresde esta solicitud
        if (!budgetRequest.getBudgets().stream().anyMatch(budget -> budget.getSupplierId().equals(supplierHired))) {
            throw new NotFoundException("El proveedor contratado no es un proveedor valido de esta solicitud");
        }
    
        budgetRequest.getBudgets().forEach(budget -> {
            if (budget.getSupplierId().equals(supplierHired)) {
                budget.setHired(true);
                budget.setState(BudgetState.ACCEPTED);
            } else {
                budget.setHired(false);
                budget.setState(BudgetState.REJECTED);
            }
        });
    }
    
    }

