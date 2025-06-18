package ar.edu.unlam.tpi.budgets.utils;

import java.util.List;

import ar.edu.unlam.tpi.budgets.model.Budget;
import ar.edu.unlam.tpi.budgets.model.BudgetRequestEntity;

public class BudgetRequestEntityHelper {
    public static BudgetRequestEntity getBudgetRequestEntity(Long budgetId, List<Budget> budgets) {
        return BudgetRequestEntity.builder()
            .id(budgetId)
            .budgets(budgets)
            .build();
    }

    public static BudgetRequestEntity getBudgetRequestEntity(Long budgetId) {
        return BudgetRequestEntity.builder()
            .id(budgetId)
            .budgets(List.of(
                    Budget.builder().build()
            ))
            .build();
    }
}
