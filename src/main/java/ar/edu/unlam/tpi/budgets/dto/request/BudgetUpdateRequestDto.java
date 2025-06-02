package ar.edu.unlam.tpi.budgets.dto.request;

import ar.edu.unlam.tpi.budgets.model.enums.BudgetState;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetUpdateRequestDto {
    @NotNull
    private BudgetState state;

    @NotNull
    private Long supplierHired;
    
}