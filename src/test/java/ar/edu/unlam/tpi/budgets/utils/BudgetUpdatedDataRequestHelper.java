package ar.edu.unlam.tpi.budgets.utils;

import ar.edu.unlam.tpi.budgets.dto.request.BudgetUpdateDataRequestDto;

public class BudgetUpdatedDataRequestHelper {
    
    public static BudgetUpdateDataRequestDto getBudgetUpdateDataRequestDto() {
        return BudgetUpdateDataRequestDto.builder()
            .price(150000f)
            .daysCount(3)
            .workerCount(1)
            .detail("Actualización de presupuesto")
            .build();
    }
}   
