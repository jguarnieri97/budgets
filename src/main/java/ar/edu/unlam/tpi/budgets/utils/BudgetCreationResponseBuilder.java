package ar.edu.unlam.tpi.budgets.utils;

import org.springframework.stereotype.Component;

import ar.edu.unlam.tpi.budgets.dto.response.BudgetCreationResponseDto;
import ar.edu.unlam.tpi.budgets.model.BudgetRequest;

@Component
public class BudgetCreationResponseBuilder {

    public BudgetCreationResponseDto build(BudgetRequest budgetRequest) {
        return BudgetCreationResponseDto.builder()
                .id(budgetRequest.getId())
                .build();
    }
}