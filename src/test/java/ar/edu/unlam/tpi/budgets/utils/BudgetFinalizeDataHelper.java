package ar.edu.unlam.tpi.budgets.utils;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetFinalizeRequestDto;

public class BudgetFinalizeDataHelper {

    public static BudgetFinalizeRequestDto getBudgetFinalizeRequestDto() {
        return BudgetFinalizeRequestDto.builder()
                .supplierHired(1L)
                .build();
    }
}
